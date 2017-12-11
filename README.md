# LCSAnimalLearning
New version of the prototype (0.9)
whats left:
-add sight consideration to awareness check
-tweak numeric values of predator and prey to enhance simulation
-ask adviser what % should be considered the same classifier
-change classifer to classifier
-optimization and comments
whats new
-sneak and adjust have been added as classifier actions
-tweaked awareness check to accurately update awareness of prey
-removed pounce as it was redundant
-classifiers now report the actual initial scenario rather than the final one
bug fixes
-fixed bug with the round function to more accurately round floats to the tens place
-fixed an issue where a payoff could be calculated to be over 1.0f
-fixed a bug where completely unaware prey (0.0f) could never increase their awareness
Misc
-Removed xcs library link and tutorial as they are now pointless
