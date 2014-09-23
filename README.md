AngryBirds-TheBlues
===================

Artificial Intelligence Project

---------------------------------------------------------------------------------------------------------------------------------
1. Introduction

The general framework to be adopted in the project is divided into two 

parts:

1) The Planning Part

2) The Learning Part

The Planner Functionality will try to get the maximum number of points by 

trying to hit the maximum number of pigs to score the maximum number of 

points.

In the Planning Part, we will try to implement a “Tree Structure Approach” to 

design an intelligent agent for the Angry Birds Domain.

The mechanism is based on an efficient tree structure for encoding and 

representing the game screenshots where it exploits advanced modelling 

capacity.

The Learner Functionality will enable the agent to have awareness about the 

surroundings and also learn from the environment.

The Learning Part will be discussed at length in the later versions of the 

document.

2. The Planning Part

In this project, tree structure is proposed for mapping scenes of game 

levels, where the nodes represent different material of solid objects. This state 

representation is informative as incorporates all the necessary knowledge 

about game snapshots, and simultaneously abstract so as to reduce the 

computational cost and accelerate the learning procedure. 

This tree representation allows the construction of an efficient and powerful 

feature space that can be used next for the prediction.

We have considered seven types of materials for objects presented in the 

game:

• Ice/Glass (I)

• Wood (W)

• Stone (S)

• Pig (P)

• TNT (T)

Our state space representation follows a tree-like structure of the game 

scene using spatial abstractions and topological information. In particular, we 

construct a tree where each node represents a union of adjacent objects of the 

same material. This is done in a hierarchical fashion (bottom-up approach). 

The root node is considered as a virtual node that communicates with orphan 

nodes, i.e. nodes which do not have any other object above.

We evaluate each node (s) of the tree using three quantities:

1) x1(s): Personal weight calculated as the product of the area. Area of the 

object with a coefficient cs which is related to the type of the objects, 

i.e. x1(s) = Area(s) × cs. 

All types of object have the same value for this coefficient, cs = 1, except 

for the types of Pig (P) and TNT (T) which have a larger value of cs = 10.

2) x2(s): Parents cumulative weight calculated by the sum of personal 

weights of the node’s parents, P(s), in the tree

3) x3(s): Distance (in pixels) to the nearest pig, normalized to [0, 1]. 

This is made dividing the original distance by 100, where we assumed 

that 100 pixels is the maximum distance in the scene among objects and 

pigs.

There is also an additional constraint termed “Feasibility” which is discussed at 

length in the Next Topic. 

The above strategy introduces an appropriate and powerful feature space for 

all the possible targets.

2.2 Feasibility

The next step to our approach is to examine each node in terms of its 

possibility to be reached. 

It is possible some obstacles and stable structures such as mountains, to be 

inserted between the slingshot and the target. 

Therefore, an examination step is required at each node so as to ensure that 

the corresponding trajectories can reach the target.

It must be noted that two different trajectories are calculated, a direct shot 

(angle <= 45◦) and a high arching shot (angle > 45◦).

Both of them are examined in order to estimate the tree’s nodes feasibility.
If there is at least one shot that could reach that node (target) directly, we 

label it as feasible, and otherwise the tree’s node is labelled as infeasible.

In the case where both trajectories are accepted, priority is given on the direct 

shot due to its effectiveness.

Finally, in the case of the white bird a node is considered as feasible if it can be 

reached by bird’s egg, as opposed to the other types of birds.
