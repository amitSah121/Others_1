import pandas as pd


def open_file(filename):
	f = None
	b = False
	if filename.split(".")[-1] == "csv":
		f = pd.read_csv(filename)
		b = True
	return f,b