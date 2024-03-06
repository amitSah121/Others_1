from kivy.app import App
from kivy.uix.screenmanager import ScreenManager, FadeTransition

from components.buttons import Nav1Button,Nav2Button
from components.table_def import TableDef

from screens.home import HomeScreen
from screens.setup import SetupScreen
from screens.analysis import AnalysisScreen

from library.open_file import open_file

import pandas as pd


class MainApp(App):
    table = []
    id_table = 0

    def build(self):
        self.home = HomeScreen(name='home')
        self.home.main = self


        self.setup = SetupScreen(name='setup')
        self.setup.main = self

        self.analysis = AnalysisScreen(name='analysis')
        self.analysis.main = self

        sm = ScreenManager(transition=FadeTransition())
        sm.add_widget(self.home)
        sm.add_widget(self.setup)
        sm.add_widget(self.analysis)

        return sm


    def add_table(self,filename):
        table,b = open_file(filename)
        if b:
            self.table.append({"table":table,"id":self.id_table,"filename":filename})
            self.home.append_table_widget({"filename":filename,"table":table,"id":self.id_table})
            self.id_table += 1 

    def get_tables(self):
        # dummy data
        # self.table.append({"table":pd.DataFrame([{"x":1,"y":"1"}]),"id":1,"filename":"Name/name"})
        return self.table



if __name__ == "__main__":
    MainApp().run()