import random
import logging
import xcs
from xcs.scenarios import ScenarioObserver
from xcs.scenarios import Scenario
from xcs.bitstrings import BitString

class HaystackTest(Scenario):
    
    def __init__(self, training_cycles=1000, input_size=500):
        self.input_size = input_size
        self.possible_actions = (True, False)
        self.initial_training_cycles = training_cycles
        self.remaining_cycles = training_cycles
        self.needle_index = random.randrange(input_size)
        self.needle_value = None

    @property
    def is_dynamic(self):
        return False
        
    def get_possible_actions(self):
        return self.possible_actions
    
    def reset(self):
        self.remaining_cycles = self.initial_training_cycles
        self.needle_index = random.randrange(self.input_size)
        
    def more(self):
        return self.remaining_cycles > 0
    
    def sense(self):
        haystack = BitString.random(self.input_size)
        self.needle_value = haystack[self.needle_index]
        return haystack
    
    def execute(self, action):
        self.remaining_cycles -= 1
        return action == self.needle_value


logging.root.setLevel(logging.INFO)

problem = HaystackTest()

xcs.test(scenario=ScenarioObserver(problem))

