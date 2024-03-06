from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.boxlayout import BoxLayout
from kivy.clock import Clock

class MainApp(App):
	def __init__(self):
		super(MainApp,self).__init__()

	def my_callback(self,dt):
		print('My callback is called', dt)

	def call1(self,widget,touch):
		if widget.collide_point(touch.x,touch.y):
			print("Hello")

	def call2(self,widget,touch):
		if widget.collide_point(touch.x,touch.y):
			print("Fello")

	def build(self):
		print("build1")
		super(MainApp,self).build()
		print("build2")
		self.b = BoxLayout()
		l1 = Label(text="Hello")
		l1.bind(on_touch_down=self.call1)
		self.b.add_widget(l1)
		self.b.add_widget(Label(text="Fello",on_touch_down=self.call2))
		event = Clock.schedule_interval(self.my_callback, 1 )
		return self.b

	def run(self):
		print("run1")
		super(MainApp,self).run()
		print("run2")

	def on_start(self):
		print("on_start1")
		super(MainApp,self).on_start()
		print("on_start2")

	def on_pause(self):
		print("on_pause1")
		super(MainApp,self).on_pause()
		print("on_pause2")

	def on_resume(self):
		print("on_resume1")
		super(MainApp,self).on_resume()
		print("on_resume2")


def my_callback(dt):
	print('My callback is called from outside ', dt)

if __name__ == "__main__":
	event = Clock.schedule_interval(my_callback, 1 )
	MainApp().run()