from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.uix.spinner import Spinner, SpinnerOption
from kivy.uix.dropdown import DropDown
from kivy.properties import ListProperty,StringProperty, ObjectProperty
from kivy.uix.checkbox import CheckBox

class FigureDef(BoxLayout):
	table_name = StringProperty()
	table = ObjectProperty(None)
	btns = ObjectProperty({})
	type_btnx = ObjectProperty({})
	type_btny = ObjectProperty({})
	already_done = ListProperty([False,False])

	def __init__(self,**kwargs):
		super(FigureDef,self).__init__(**kwargs)

	def x_selected(self,x_opt):
		if not self.already_done[0]:
			self.already_done[0] = True
			for i,x in enumerate(self.table.columns):
				# print(i,self.table.dtypes[i])
				btn = Button(text=str(x),size_hint=(None,None),background_normal="",color=(0,0,0,1))
				btn.bind(on_press=self.change_color)
				self.ids.x_stack.add_widget(btn)
				btn.size = [len(btn.text)*10,30]
				if not x_opt.text in str(self.table.dtypes[i]):
					btn.opacity = 0
					btn.size = (0,0)
				self.type_btnx[btn] = str(self.table.dtypes[i])
		else:
			for x in self.type_btnx:
				if x_opt.text in self.type_btnx[x]:
					x.opacity = 1
					x.size = [len(x.text)*10,30]
				else:
					x.opacity = 0
					x.size = [0,0]

	def y_selected(self,y_opt):
		if not self.already_done[1]:
			self.already_done[1] = True
			for i,x in enumerate(self.table.columns):
				# print(i,self.table.dtypes[i])
				btn = Button(text=str(x),size_hint=(None,None),background_normal="",color=(0,0,0,1))
				btn.bind(on_press=self.change_color)
				self.ids.y_stack.add_widget(btn)
				btn.size = [len(btn.text)*10,30]
				if not y_opt.text in str(self.table.dtypes[i]):
					btn.opacity = 0
					btn.size = (0,0)
				self.type_btny[btn] = str(self.table.dtypes[i])
		else:
			for x in self.type_btny:
				if y_opt.text in self.type_btny[x]:
					x.opacity = 1
					x.size = [len(x.text)*10,30]
				else:
					x.opacity = 0
					x.size = [0,0]

	def change_color(self,btn):
		if not str(btn) in self.btns:
			self.btns[str(btn)] = True
			btn.background_color = (0.6,0.6,0.6,1)
		else:
			if not self.btns[str(btn)]:
				self.btns[str(btn)] = True
				btn.background_color = (0.6,0.6,0.6,1)
			else:
				self.btns[str(btn)] = False
				btn.background_color = (1,1,1,1)

	def draw_figure(self):
		pass





class FigLabel(Label):
	pass

class FigButton(Button):
	pass


class FigSpinnerOptions(SpinnerOption):

    def __init__(self, **kwargs):
        super(FigSpinnerOptions, self).__init__(**kwargs)
        self.background_normal = ''
        self.background_color = (0.682, 0.682, 0.682, 1)    # blue colour
        self.height = 26
        self.color = (0,0,0,1)


class FigSpinnerDropdown(DropDown):

    def __init__(self, **kwargs):
        super(FigSpinnerDropdown, self).__init__(**kwargs)
        self.auto_width = False
        self.width = 150

class FigSpinner(Spinner):
	list = ListProperty()

	def __init__(self,**kwargs):
		super(FigSpinner,self).__init__(**kwargs)
		self.list = ['Option 1', 'Option 2', 'Option 3']
		self.dropdown_cls = FigSpinnerDropdown
		self.option_cls = FigSpinnerOptions

