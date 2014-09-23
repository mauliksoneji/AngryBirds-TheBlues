AngryBirds-TheBlues
===================
Description:
===================

ANGRYBIRDS AI AGENT FRAMEWORK
Copyright © 2014, XiaoYu (Gary) Ge, Stephen Gould, Jochen Renz, Sahan Abeyasinghe, Jim Keys, Andrew Wang, Peng Zhang. All rights reserved.
This software contains a framework for developing AI agents capable of
playing Angry Birds. The framework is composed of a javascript plugin
for the Chrome web browser and Java client/server code for interfacing
to the game and implementing the AI strategy. A sample agent is provided.

* The details of the server/client protocols can be found in ./doc/ServerClientProtocols.pdf

* We provide a sample agent communicating with the server by the pre-defined protocols. The implementation can be found in ./src/ab/demo/ClientNaiveAgent.java

* We provide a wrapper class in java that can encode/decode the communicating messages. The wrapper can be found in ./src/ab/demo/other/{ClientActionRobot.java , ClientActionRobotJava.java}

* We provide a sample C++ client that demonstrates how to connect to the server and send a doScreenShot message. It can be found in ./src/ab/demo/abClientExample.cpp

===================   Commands of the server/client version =====================================================

* Please start the server first, and then start your client. 

To start the server: java -jar ABServer.jar

To start the naive agent (server/client version): java -jar ABSoftware.jar -nasc [IP]

*You do not need to specify the IP when the server is running on localhost 

=====================  Commands of the standalone version =======================================================
  
  java -jar ABSoftware.jar -na                // run the agent from level 1
   
  java -jar ABSoftware.jar -na [0-21]       	// run the agent from the specified level.

  java -jar ABSoftware.jar -na [0-21] -showMBR     // run the agent with the real-time vision output (MBR segmentation)

  java -jar ABSoftware.jar -na [0-21] -showReal // run the agent with the real-time vision output (Real shape segmentation)

  java -jar ABSoftware.jar -showMBR        // show the real-time MBR segmentation

  java -jar ABSoftware.jar -showReal       // show the real-time real shape segmentation

  java -jar ABSoftware.jar -showTraj        // show the real-time trajectory prediction

  java -jar ABSoftware.jar -recordImg [directory] // save the current game image to the specified directory


================================  Outline of the source files ====================================================

The src folder contains all the java source codes of the software. 

The following are the files you may want to modify:

========= Files under ./src/ab/demo/ =====================				
ClientNaiveAgent.java : A server/client version of the Naive agent that interacts with the server by the pre-defined protocols

NaiveAgent.java : A standardalone implementation of the Naive agent. You can run this agent without the server.

======== Files under ./src/ab/demo/other/ ================

ActionRobot.java : A java util class for the standalone version. It provides common functions an agent would use. E.g. get the screenshot

ClientActionRobot.java : A server/client version of the java util class that encodes client messages and decodes the corresponding server messages complying with the protocols. Its subclass is ClientActionRobotJava.java which decodes the received server messages into java objects.

LoadingLevelSchema.java / RestartLevelSchema.java / ShootingSchema.java : Those files are only for the standalone version. A standalone agent can use the schemas respectively to load levels, restart levels, and launch birds. 

======== Files under ./src/ab/planner/ ====================

TrajectoryPlanner.java : Implementation of the trajectory module

=======  Files under ./src/ab/vision ======================

Vision.java ： the entry of the vision module. 

VisionMBR.java : image segmenting component that outputs a list of MBRs of a screenshot

VisionRealShape.java: image segmenting component that outputs a list of real shapes of a screenshot

=======  Other Useful methods in /src/ab/utils/ABUtil.java =========================================

isSupport(ABObject object, ABObject support): returns true if support directly supports object

getSupports(ABObject object, List blocksList): returns a list containing the subset of blocksList that support object 
 
isReachable(Vision vision, Point target, Shot shot): returns true if the target is reachable (without obstruction) by the shot

====== Files under ./src/external ===========================

ClientMessageEncoder.java : encode the client messages according to the protocols

ClientMessageTable.java : a table that maintains all the client messages and its corresponding MIDs.
Artificial Intelligence Project

---------------------------------------------------------------------------------------------------------------------------------
Strategy:
===================
1. Introduction
===================

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
===================
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
===================
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
