README FOR WIIREMOTEJ LIBRARY v1.6
2/27/09

WHAT IS WIIREMOTEJ
WiiRemoteJ is a free Java API and library for interacting with a Nintendo(R) Wii Remote (tm) and Balance Board (tm) through Bluetooth(R). WiiRemoteJ aims to do two things: first, it aims to create an easily accessible interface for Java developers wanting to work with the Wii Remote or Balance Board. Second, it aims to provide tools for developers using these devices to speed development. WiiRemoteJ comes with a complete javadoc, detailing every public class, field, and method. In addition, WiiRemoteJ aims to duplicate many of the same ideologies and methods used in standard Java libraries. This should allow developers to jump into the development process relatively quickly.

INCLUDED FILES
WiiRemoteJ should include the following files.
-Audio.au: A 40 second clip of "Tonight is the Night" by La Bouche to demonstrate the sound quality capable of the Wii Remote speaker (used by the sample code). The audio is encoded in signed 8-bit PCM at 2 kHz.
-BBImpl.java: A sample class to demonstrate WiiRemoteJ with a Balance board.
-changelog.txt: a list of changes from previous versions of WiiRemoteJ.
-docs: the folder containing the Javadoc API for WiiRemoteJ
-LICENSE.txt: the license. Read this before using WiiRemoteJ!
-Mouse Testing Layout.rtf: the layout of the mice in the mouse-testing portion of the sample class, WRLImpl. The abbreviations in the mouse-testing GUI brought up with Home and Plus are explained here.
-README.txt: This readme.
-WiiRemtoeJ.jar: The WiiRemoteJ library.
-WRLImpl.java: A sample class to demonstrate WiiRemoteJ with a Wii Remote.

REQUIREMENTS
-Java JRE 1.5 or later (JDK necessary for development).
-Bluetooth. Any computer using WiiRemoteJ must have a compatible Bluetooth dongle (internal or external).
-An implementation of JSR082 (aka Bluetooth for Java). See ABOUT JSR082 below for details.
-A Nintendo Wii Remote or Balance Board.

ABOUT JSR082
The JSR082 API provides an interface for Java to access the Bluetooth hardware on your system. At the time of this writing, there are very few implementations available. There are currently two options, depending on your platform: 1. BlueCove (available from http://www.bluecove.org) is a free download for Mac and PC. There have been a few minor issues with it, but it generally works pretty well. On Mac OS X, the built-in stack will work just fine. On Windows, you MUST obtain a separate Bluetooth stack. You should be using the WIDOMM stack, as it is the only one fully supported by both Avetana and BlueCove.  Another option is Avetana Bluetooth, but it has a pricetag (not a very big one, but it has one nonetheless) unless you're on Linux. If you are on Linux, there is a free implementation of Avetana Bluetooth. Note: it is your responsibility to find a copy of JSR082. Don't ask me for help in that area.

So, once you have your JSR082, what do you do with it? The best approach is to put it in your extensions folder. Then you won't need to specify its location every time you launch your program.
On Linux and Windows, your extensions folder is located at: JAVAFOLDER/lib/ext/
On Mac OS X, your extensions folder is located at: /System/Library/Java/Extensions/

Alternatively, you can specify the library in your classpath whenever you run an application that uses it (including WiiRemoteJ itself). For details on doing this, please consult the documentation accompanying your IDE or whatever you are using for compilation/execution of your Java programs.

Note for Mac OS X PowerPC (PPC) users: you will have to delete the Wii Remote from your previous devices list in System Preferences->Bluetooth->Devices after each connection (every time you connect a given Wii Remote). Even then, the outgoing connection will still fail every other time. There is some sort of bug relating to HCI devices and Apple's library. Luckily, it was fixed for the Intel Macs.

A general note on connecting the Wii Remote: the connection seems to work better when you wait a few seconds after device discovery begins to try to connect the remote. As a general guideline, wait about five seconds after calling findRemote before connecting the remote (more details on connection later).

USING WIIREMOTEJ
To use WiiRemoteJ, you either need to add it to your extensions folder (see above under ABOUT JSR082) or specify it in your classpath for compiling and executing your application. If you are using an IDE, consult your IDE documentation for details. If you are using the commandline tools javac and java, please read the following.

To compile your application with WiiRemoteJ in the classpath, type the following: javac -cp PATH/TO/WiiRemoteJ.jar <classes to compile>
To execute your application with WiiRemoteJ in the classpath, type the following: java -cp PATH/TO/YOUR/CLASSES/;PATH/TO/WiiRemoteJ.jar
If you did not add your JSR082 implementation to your extensions folder, you will have to specify its location as well, by adding a semi-colon after the path to WiiRemoteJ.jar, and then adding the path to your JSR082 implementation.

To compile and run WRLImpl (assuming you have placed your JSR082 implementation in the extensions folder and assuming the library is in the same directory as WRLImpl.java), you would do the following:
javac -cp WiiRemoteJ.jar WRLImpl.java
java -cp ./;WiiRemoteJ.jar WRLImpl

Note: on the Mac, replace all semi-colons (;) above with colons (:).

CONNECTING A WII REMOTE
In case it isn't clear, this is how you connect your Wii Remote: 
1. Call one of the findRemote methods.
2. Push the 1 and 2 buttons on your Wii Remote simultaneously or press the red button under the battery case (you should probably wait a few seconds after calling findRemote before doing this).
3. The Wii Remote will be returned (findRemote() method) or wiiDeviceDiscovered(...) will be fired by the specified WiiDeviceDiscoveryListener (either of the findRemotes(...) methods).

CONNECTING A BALANCE BOARD
The connection process is similar to that of a Wii Remote. Simply call findBalanceBoard(), then push the button to sync the Balance Board to the Wii (yes, you must push the red sync button in the battery case every time). A BalanceBoard object should be returned.

Important note: DO NOT connect the Wii Remote/Balance Board via your computer's bluetooth programs or utilities. WiiRemoteJ handles the entire connection process. If you connect the device via the computer's utilities, WiiRemoteJ will not be able to connect to it (WiiRemoteJ will throw an exception).

Note that if you have a Wii running and a remote/board has been synced to it, it will connect to the Wii and not your computer. Simply turn the Wii off, and then connect the remote/board. You can then turn the Wii on again if you like (if you are using its sensor bar, for instance).

PROGRAMMING WITH WIIREMOTEJ
Please read the Javadoc before you do anything. You don't have to read the whole thing, but the WiiRemote class section is definitely a must. It contains all basic information about programming with WiiRemoteJ.

Also, a note about reports. "Reports" are channel-like things that indicate what type of input you are receiving from the remote. There are many reports available, but even so, some input combinations do not work. Please take a look at the various reports (and their associated input combinations) in the documentation for the WiiRemote class. The library will handle report-switching for you, but you need to be aware of what combinations are allowed, as you'll end up throwing a lot of IllegalStateExceptions otherwise (e.g. if you try to use Extended IR mode when the accelerometer is enabled; there's no report that supports extended IR and the accelerometer).

ASSORTED NOTES
If you want to use IR functionality, but you don't have a Wii or some sort of sensor bar, you can use a pair of candles. You really just need an IR light source of some sort. Some flashlights and some sorts of lamps work as well. You can also order wireless sensor bars online.

Interleaved input for the accelerometer and full IR mode are implemented, but not tested.

ADPCM sound format for speaker playback is implemented, but has not been tested.

I have implemented Guitar Hero World Tour Guitar and Drums, but I have not tested either the touch bar on the World Tour Guitar or the Drums. Be advised and please report any bugs.

The scroll wheel directions are reversed on the Mac. This is a fault of Apple's implementation and will hopefully be fixed in Leopard (with Java 6).

Because I was lazy at the time, my WiiRemoteListener and my main class are the same class, WRLImpl. This is not necessary, nor is it even advised. You should probably put your WiiRemoteListener in its own file, as it will probably end up being quite lengthy. I also apologize that WRLImpl is such a mess in general. I hope to put together some proper sample code in the future.

CREDITS
The WiiRemoteJ library and all associated assets were developed by me, Michael Diamond (aka Cha0s). However, I could not have developed WiiRemoteJ without beta testers. First, the library would have been very buggy, and second, I would not have been pushed to add all of the features I did. So a big thank you to them. The following people signed up to test and emailed me at least one bug report (so if you don't see your name here, it's because I didn't get any bug reports from you):
Amablue
Andrew Metcalf
Blade
Cantimatt
Classiclll
Dasickis
David Talbot (aka Tet)
ErikHK
rj_eui

Of these many testers, two deserve special mention.
First, my good friend Andrew Metcalf. He let me use his computer and his house during the time that Avetana wouldn't work on my computer. For that (among other things), I am extremely grateful. He also was my source for Classic Controller testing. Finally, he did testing on the fly for me whenever I asked for confirmation of an issue or help tracking down a bug. The development process would have taken much longer without his help.

Second, I must mention David Talbot. I received over thirty emails from him throughout the testing process, and he was always involved and quick to send feedback. He went above and beyond the normal tester, performing efficiency checks, questioning my methods, catching innumerable bugs, and providing invaluable feedback.
To David and Andrew: thank you profusely.

Also, a special thanks to Classiclll for translating the docs into Japanese. A big thank you in any case!

CONCLUSION
That's about it. I really don't have much else to say. Please feel free to send me any bug reports or questions. My email address is included in the license document. You can also post in the WiiRemoteJ section on WiiLi (http://www.wiili.org/forum), as I read it regularly. Good luck in all your development ventures with WiiRemoteJ!

Please see LICENSE.txt for trademark information.