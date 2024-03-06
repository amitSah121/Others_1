import random
from typing import NoReturn
from kivy.lang import Builder
from kivy.metrics import dp
from kivy.properties import ListProperty, StringProperty, NumericProperty
from kivy.clock import Clock, _default_time as time
from kivy.uix.recycleview.views import RecycleDataViewBehavior
from kivymd.app import MDApp
from kivymd.uix.behaviors import RoundedRectangularElevationBehavior
from kivymd.uix.boxlayout import MDBoxLayout
from kivymd.uix.card import MDCard
from kivymd.uix.label import MDLabel

MAX_TIME = 1 / 60

kv = """
#:import random random

<CustomCell>:
    id: custom_cell
    orientation: "vertical"
    md_bg_color: (random.random(), random.random(), random.random(), 0.3)

    MDBoxLayout:
        id: column
        orientation: "horizontal"
        
        MDLabel:
            id: column_text


<CustomRow>:
    id: custom_row
    orientation: "vertical"
    size_max_hint_y: 50

    MDBoxLayout:
        id: row
        orientation: "horizontal"

    MDSeparator:


<CustomTable>
    orientation: "vertical"
    padding: 35, 5, 35, 5
    elevation: 5
    radius: 28

    MDGridLayout:
        id: header
        orientation: 'lr-tb'
        rows: 1
        cols: root._columns_number
        size_hint_max_y: 50

    MDSeparator:
        color: app.theme_cls.primary_light

    RecycleView:
        id: table_list
        viewclass: "CustomCell"
        scroll_wheel_distance: 50
        always_overscroll: True
        effect_cls:  "ScrollEffect"
        bar_width: 10
        scroll_type: ["bars", "content"]

        RecycleGridLayout:
            id: table_list_recycle
            size_hint: 1, None
            cols: root._columns_number
            height: self.minimum_height
            default_size_hint: 1, None
            default_size: None, 50

    MDSeparator:
        color: app.theme_cls.primary_light

    MDBoxLayout:
        size_hint_max_y: 50

MDBoxLayout:
    orientation: 'vertical'
    padding: (10, 10)

    MDBoxLayout:
        id: main_box

    MDBoxLayout:
        id: controls_box
        orientation: 'horizontal'
        size_hint_max_y: 50

        MDFlatButton:
            text: 'Add rows'
            on_release: app.add_rows()
"""


class CustomCell(RecycleDataViewBehavior, MDBoxLayout):
    text = StringProperty("")
    index = None

    def __init__(self, *args, **kwargs):
        super(CustomCell, self).__init__(*args, **kwargs)
        self.bind(on_text=self.on_text)
        self.bind()

    def on_text(self, *args, **kwargs) -> NoReturn:
        self.ids.column_text.text = self.text

    def refresh_view_attrs(self, rv, index, data):
        self.index = index
        return super(CustomCell, self).refresh_view_attrs(rv, index, data)


class CustomTable(RoundedRectangularElevationBehavior, MDCard):
    row_data = ListProperty()
    column_data = ListProperty()
    _columns_number = NumericProperty(0)
    _column_sizes = ListProperty()

    def __init__(self):
        super(CustomTable, self).__init__()
        self.bind(column_data=self.on_cols_data)
        self.bind(row_data=self.on_row_data)

    def on_cols_data(self, *args, **kwargs):
        self._columns_number = len(self.column_data)
        self.ids.table_list_recycle = self._columns_number

        # Create headers widgets
        header_add_widget = self.ids.header.add_widget
        for col in self.column_data:
            if isinstance(col, tuple) and len(col) == 2:
                # Add size to list of sizes if we are given a size
                self._column_sizes.append(col)

                # Add text widget
                header_add_widget(
                    MDLabel(text=col[0], bold=True, size_hint_max_x=col[1])
                )
            else:
                raise AssertionError(
                    "Column definitions must have both a column name and size."
                    "If no size is needed"
                )

    def on_row_data(self, *args, **kwargs):
        # Make a value copy of the data
        temp_rows = list(self.row_data)

        # Add rows using producer/ consumer model
        while temp_rows and time() < (Clock.get_time() + MAX_TIME):
            _row = temp_rows.pop(0)
            for column in _row:
                column_size = self._column_sizes[_row.index(column)][1]
                if column_size is not None:
                    self.ids.table_list.data.append(
                        {
                            **column,
                            "size_hint_max_x": column_size,
                        }
                    )
                else:
                    self.ids.table_list.data.append({**column})


class Example(MDApp):
    table = None

    def build(self):
        builder = Builder.load_string(kv)
        self.table = CustomTable()
        builder.ids["main_box"].add_widget(self.table)
        self.table.column_data = [
            ("ID", dp(75)),
            ("First Name", None),
            ("Last Name", None),
            ("Email", None),
        ]
        return builder

    def add_rows(self):
        table_data = [
            [{"text": str(random.randint(1, 100))} for _ in range(4)]
            for _ in range(random.randint(1, 100))
        ]
        self.table.row_data = table_data


if __name__ == "__main__":
    Example().run()