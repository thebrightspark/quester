-----------------------------
Quester Questing System API
=============================
About:
Quester is a modding "sub-API" (an API for an API) that will allow Forge modders to easily add in quests to Minecraft without having to create their own system.

Features:
------------------
* Quests Have A Template Interface That Must Be Implemented In Order To Be Added To The Registry
* A Quest Registry System
* A Capability To Track A Player's Completed Quests
* Commands To Show The Completed Quests And To Reset Them For Testing

---------------------

Bug Fixes As of Late:
---------------------

* NBT Fixed So That Quests Are Saved Into 'Incomplete' And 'Complete' Sets And Then Added To An NBT Tag List
* Future Bugs Have Been Prevented By Separating The Quests Into 'Incomplete' And 'Complete' And Is Now Easier To Manage
