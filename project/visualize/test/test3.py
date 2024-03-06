import pandas as pd
# import dtale


df = pd.read_csv('/home/groupe/linux/vs_code/python3/visualize/resources/dataset/business-financial-data-september-2023-quarter.csv')

# dt = dtale.show(df)  # create dtale with our df
# dt.open_browser()  # bring it to a new tab (optional)

# df = dt.data  # connect all the updates from dtale gui and our df 

print(df.iloc[0].to_string())
     