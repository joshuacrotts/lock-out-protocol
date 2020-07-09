<h1 align="center">Lock Out Protocol</h1>

<p align="center">
  <img width="460" height="300" src="lock_out_protocol.gif">
</p>

[![CodeFactor](https://www.codefactor.io/repository/github/joshuacrotts/lock-out-protocol/badge)](https://www.codefactor.io/repository/github/joshuacrotts/Lock-out-protocol) ![GitHub contributors](https://img.shields.io/github/contributors/JoshuaCrotts/Lock-Out-Protocol) ![GitHub commit activity](https://img.shields.io/github/commit-activity/JoshuaCrotts/Lock-Out-Protocol) ![GitHub repo size](https://img.shields.io/github/repo-size/JoshuaCrotts/Lock-Out-Protocol)  ![](https://tokei.rs/b1/github/JoshuaCrotts/Lock-Out-Protocol) ![](https://tokei.rs/b1/github/JoshuaCrotts/Lock-Out-Protocol?category=files) [![GitHub issues open](https://img.shields.io/github/issues/JoshuaCrotts/Lock-Out-Protocol)]() 
[![GitHub issues closed](https://img.shields.io/github/issues-closed-raw/JoshuaCrotts/Lock-Out-Protocol)]()

Lock Out Protocol is a 2D-zombie survival game, played from a top-down perspective. Lock Out Protocol is a joint effort of programming, designing, and planning between [Ronald Abrams](https://github.com/rgabrams), [Rinty Chowdhury](https://github.com/rintychy), and [Joshua Crotts](https://github.com/JoshuaCrotts) for CSC - 340: Software Engineering at the University of North Carolina at Greensboro. The underlying engine stems from one of Joshua's earlier projects (in conjunction with [Andrew Matzureff](https://github.com/AndrewMatzureff)) known as the [Standards](https://github.com/JoshuaCrotts/Standards) library. For this project, Standards is used as an abstract concept and tool, since the predominant goal of this project is to develop an application, not an engine _for_ said application. More specifically, there are a plethra of concepts, optimizations, and miscellaneous ideas that go into crafting a game engine. As such, one semester is not nearly enough time to make a proper game, along with its complex engine.

## Dependencies
The following .jar files are required for building the project. These should be placed in a folder in the root of your development project called libraries/. 

1. [Apache Commons Math 3.6.1](https://mvnrepository.com/artifact/org.apache.commons/commons-math3/3.6.1)
2. [JBCrypt-0.4](https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4)
3. [JSON 2014-01-07](https://mvnrepository.com/artifact/org.json/json/20140107)
4. [MySQL Connector Bin](http://www.java2s.com/Code/Jar/m/Downloadmysqlconnectorjava5124binjar.htm)
5. [MySQL Socket Factory Connector](https://jar-download.com/artifacts/com.google.cloud.sql/mysql-socket-factory-connector-j-8/1.0.11/source-code)
6. [Standards](https://github.com/JoshuaCrotts/Standards/blob/development/dist/Standards.jar)

## Rebuilding Lock Out Protocol

To rebuild the code in NetBeans 8.2, Java 8 is required (either Oracle, Amazon Corretto, or OpenJDK). Any arbitrary flavor of Java 8 is most likely acceptable.

Create a new project in any IDE, then clone the repository inside of **the src/ folder of the aforesaid project**. This step is absolutely critical. To ensure the project remains IDE-independent, this is the procedure for downloading the code. At this point, all files should be present and available to compile and run. For any of the above dependencies, make a folder called libraries/ in the root of the project, download those .jars, and place them in there. You may have to modify your IDE's project to account for compile-time libraries, so be sure to do that, so the IDE knows they exist.

## Reporting Bugs

See the Issues Tab.

## Version History
The **master** branch encompasses significant development changes in the project, whereas the three other branches house various experimentation and states of progression. These branch are constantly evolving. Rarely (whenever it is best), the master branch is updated to mimic one of these three branches.
