import pandas as pd
import numpy as np
from datetime import datetime, timedelta
from sqlalchemy import create_engine
from sklearn.linear_model import LinearRegression
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()
app.add_middleware(CORSMiddleware, allow_origins=["*"], allow_methods=["*"], allow_headers=["*"])

# Connexion PostgreSQL
engine = create_engine("postgresql+psycopg2://m1:p12@localhost:5432/grossi")
# Calculer la date 2 semaines avant aujourd'hui
two_weeks_ago = datetime.now() - timedelta(weeks=2)

@app.get("/predict_stock")
def predict_stock():
    # Charger les données depuis la table stock
    df = pd.read_sql(
    f"SELECT produit_id, type_mouvement_id, quantite, date_mouvement "
    f"FROM stock "
    f"WHERE date_mouvement >= '{two_weeks_ago.strftime('%Y-%m-%d')}'",
    engine
    )

    
    # Calcul du stock cumulé pour chaque produit
    df['quantite_signed'] = np.where(df['type_mouvement_id'].isin([1,5]), df['quantite'], -df['quantite'])
    
    result_list = []

    for pid, df_prod in df.groupby('produit_id'):
        # Trier par date
        df_prod = df_prod.sort_values('date_mouvement').copy()
        df_prod['stock_cumulatif'] = df_prod['quantite_signed'].cumsum()
        
        # Créer la colonne 'jours' relative au premier mouvement
        df_prod['jours'] = (df_prod['date_mouvement'] - df_prod['date_mouvement'].min()).dt.days
        
        # Supprimer les lignes avec NaN
        df_prod = df_prod.dropna(subset=['jours', 'stock_cumulatif'])
        
        if len(df_prod) < 2:
            stock_prev = df_prod['stock_cumulatif'].iloc[-1] if len(df_prod) > 0 else 0
        else:
            X = df_prod[['jours']].values
            y = df_prod['stock_cumulatif'].values
            model = LinearRegression()
            model.fit(X, y)
            next_day = np.array([[df_prod['jours'].max() + 1]])
            stock_prev = float(model.predict(next_day)[0])
        
        result_list.append({
            "produit_id": int(pid),
            "stock_actuel": float(df_prod['stock_cumulatif'].iloc[-1]),
            "stock_prev": stock_prev
        })

    return result_list
