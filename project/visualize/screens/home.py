from kivy.uix.screenmanager import ScreenManager, Screen
from kivy.uix.popup import Popup
from components.table_def import TableDef
from components.dialog_box import LoadDialog

import os

class HomeScreen(Screen):
	main = None

	def get_file(self):
		content = LoadDialog(load=self.load_file, cancel=self.dismiss_popup)
		p = self._popup = Popup(title="File Browser", content=content,size_hint=(0.9, 0.9))
		self._popup.open()

	def load_file(self, path, filename):
		self.main.add_table(filename[0])
		self._popup.dismiss()

	def dismiss_popup(self):
		self._popup.dismiss()

	def append_table_widget(self,table):
		t = table["table"]
		p = table["id"]
		p1 = TableDef()
		p1.set_name(table["filename"].split("/")[-1])
		p1.set_table(t,p)
		self.ids.tables_info.add_widget(p1)


	def print_(self):
		print(self.ids)


	

	



