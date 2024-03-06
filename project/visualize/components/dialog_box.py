
from kivy.uix.floatlayout import FloatLayout
from kivy.properties import ObjectProperty
from kivy.uix.button import Button
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.recycleview import RecycleView
from kivy.uix.recycleview.views import RecycleDataViewBehavior
from kivy.properties import BooleanProperty, NumericProperty, StringProperty, ListProperty
from kivy.app import App


class LoadDialog(FloatLayout):
	load = ObjectProperty(None)
	cancel = ObjectProperty(None)


class TextInputForSheet(Button):
	font_size = NumericProperty()
	x_ = NumericProperty()
	y_ = NumericProperty()

	def call_on_press(self):
		App.get_running_app().sheet.edit_cell(self)

class MenuBarSheet(BoxLayout):
	top_level = ObjectProperty()

	def set_text(self,value):
		self.top_level.set_cell_text(self,value)

	def save(self):
		self.top_level.save_file()

class SpreadSheetDialogBox(RecycleView):
	save = ObjectProperty()
	cancel = ObjectProperty()
	entry = ObjectProperty()
	df = ObjectProperty()
	cols = NumericProperty()
	top_level = ObjectProperty()
	prev_cell = ObjectProperty()

	def __init__(self, **kwargs):
		super(SpreadSheetDialogBox, self).__init__(**kwargs)
		self.set_data()
		self.cols = len(self.df.columns) + 1
		# print(f"columns = {self.cols}")
		App.get_running_app().sheet = self

	def set_data(self):
		self.data = []
		self.data.append({"text":"S.N.","font_size":30,"x_":-1,"y_":-1})
		for i in range(len(self.df.columns)):
			p1 = self.df.columns[i]
			if "NaN" in str(p1):
				print(p1)
				p1 = str(" None ")
			else:
				p1 = str(p1)
			self.data.append({"text":p1,"font_size":30,"x_":-1,"y_":-1})
		for i in range(len(self.df.index)):
			# print(f"i = {i}")
			j = 0
			self.data.append({"text":f"{i}","font_size":18,"x_":-1,"y_":-1})
			for x in self.df.iloc[i]:
				p1 = x
				if "nan" in str(p1):
					# print(p1)
					p1 = str(" None ")
				else:
					p1 = str(p1)
				p1 = p1.replace("\n","")
				p1 = self.convert_to_ascii(p1)
				self.data.append({"text":p1,"font_size":18,"x_":j,"y_":i})
				j+=1
		# print(self.data)


	def edit_cell(self,cell):
		if(self.prev_cell != None):
			self.prev_cell.background_color=(1,1,1)
		self.prev_cell = cell
		cell.background_color=(0.9,0.9,0.9)
		self.top_level.edit_cell(cell)



	def convert_to_ascii(self,string):
		ascii_string = []
		for character in string:
			ascii_string.append(chr(ord(character)))
		return "".join(ascii_string)


class TableSelectionDialogBox(BoxLayout):
	cancel = ObjectProperty()

	def selected(self):
		pass