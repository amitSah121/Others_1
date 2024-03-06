from kivy.uix.screenmanager import ScreenManager, Screen
from components.figure import FigureDef
from components.dialog_box import TableSelectionDialogBox
from kivy.uix.popup import Popup
from kivy.uix.button import Button


class AnalysisScreen(Screen):
	main = None
	figs = []

	def add_figure(self):
		self.content = TableSelectionDialogBox(cancel=self.dismiss_popup)
		lst = self.content.ids.list
		for i,x in enumerate(self.main.get_tables()):
			a1 = x["filename"].split("/")[-1]
			a2 = x["id"]
			b1 = Button(text=f"{i} : {a1}  id = {a2}",size_hint_y=None,padding=(10,10,10,10),color=(0,0,0,1),font_size=18,background_normal="") 
			b1.height = 40
			b1.bind(on_press =self.load_table)
			lst.add_widget(b1)
		p = self._popup = Popup(title="Select Table", content=self.content,size_hint=(0.9, 0.9))
		self._popup.open()

	def load_table(self, btn):
		pos = int(btn.text.split(" ")[0])
		table = self.main.get_tables()[pos]
		# print()
		self.append_figure(table)
		self._popup.dismiss()

	def dismiss_popup(self):
		self._popup.dismiss()

	def append_figure(self,table):
		fig = FigureDef(table_name=table["filename"].split("/")[-1])
		df = fig.table = table["table"]
		# type_list = fig.table["table"]
		types = {str(x) for x in df.dtypes.tolist()}
		fig.ids.my_x_spinner.values = list(types)
		fig.ids.my_y_spinner.values = list(types)
		self.ids.figure_info.add_widget(fig)

		self.figs.append(fig)

