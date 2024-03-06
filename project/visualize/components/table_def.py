from kivy.uix.boxlayout import BoxLayout
from kivy.uix.gridlayout import GridLayout
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.uix.textinput import TextInput
import pandas as pd
from kivy.uix.popup import Popup
from components.dialog_box import SpreadSheetDialogBox,MenuBarSheet
from kivy.properties import StringProperty

class TableDef(BoxLayout):
	on = True
	caret_right = "resources/images/caret_right.png"
	caret_down = "resources/images/caret_down.png"
	edit = StringProperty("resources/images/edit.png")
	caret_ = StringProperty(caret_right)
	text = StringProperty("Defination")
	description = StringProperty("")
	table = None
	id = None
	# button_on_focus = None
	# changed_cells = []



	def toggle(self):
		self.on = not self.on
		if self.on: 
			self.caret_ = self.caret_right
			self.description = ""
		else:
			self.caret_ = self.caret_down
			self.description = "Description"
		# print(self.on)

	def set_name(self,text):
		self.text = text

	def set_table(self, table, id):
		self.table = table
		self.id = id

	def open_sheet(self):
		pass

	def open_sheet(self):

		data = self.table
		df = data #pd.DataFrame(data)
		self.df = df
		self.content = BoxLayout(orientation="vertical")
		self.one_more = MenuBarSheet(orientation="horizontal")
		self.one_more.ids.inp.multiline = False
		self.content.add_widget(self.one_more)
		self.sheet = SpreadSheetDialogBox(df=df, top_level=self, save=self.save_file, cancel=self.dismiss_popup, entry=self.entry)
		self.sheet.stroke_width = 2
		self.sheet.stroke_color = (1, 0, 0, 1)
		self.one_more.top_level = self
		self.content.add_widget(self.sheet)

		p = self._popup = Popup(title="SpreadSheet", content=self.content,size_hint=(0.9, 0.9))
		self._popup.open()

	def edit_cell(self,cell):
		self.one_more.ids.inp.text = cell.text

	def set_cell_text(self,instance,value):
		if(self.sheet.prev_cell != None):
			self.sheet.prev_cell.text = value
			x = self.sheet.prev_cell.x_
			y = self.sheet.prev_cell.y_
			# self.df.iloc[y_][x_] = value
			# print(self.df.iloc[y][x])

	def save_file(self):
		if(self.sheet.prev_cell != None):
			print(self.sheet.prev_cell.x_)

	def dismiss_popup(self):
		self._popup.dismiss()

	def entry(self,instance, value):
		# self.button_on_focus.text = self.content.ids.input.text
		# p = self.content.ids.selected.text.split(",")
		# self.changed_cells.append((int(p[0]),int(p[1]),self.content.ids.input.text))
		pass


