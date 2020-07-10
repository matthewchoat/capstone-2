<h1>The Problem Solving Framework : 'UPER'</h1>

* U = "Understand"
* P = "Plan"
* E = "Execute"
* R = "Reflect" / "Refactor"

<h2>1. Understanding the Problem</h2>
<P>* So I need to either build an ASCII terminal based game or a GUI-based game using a third-pary Java Framework.</p>
<P>* This program needs to utilize at least one stream/lambda function and an abstract class/interface. </p>
<P>* I will not only need to finish all the requirements before my software presentation, but will also need to complete my UPER, architecture outline, and game instructions documents.</p>
<P>* I will also need to keep OOP Pillars in mind going forward as I plan and build my game.</p>
<P>* Even if the code isn't perfect, I still need to deliver a program that runs without any bugs or glitches in the UI or gameplay.</p>
<P>* I will need to be able to explain my code thoroughly, so will need to comment my classes and methods heavily and try to use easy to understand naming conventions.</p>
<P>* If I want to get all the bonus points possible, I will also need to host my game using a React app.</p>
<P></p>* 
<h2>
    2. Planning the Solution
</h2>
<P>* So, I actually do have a good amount of game design and development experience in other programming languages. In Ruby, I've built an interactive Blackjack game and an online multiplayer chess game using Ruby on Rails and Javascript. I've also written an RPG game using Ruby scripting in RPG Maker 6.0.</p>
<P>* The last game I made was an online Javascript Tetris game. I made this game for practice during our Javascript modules at TEKcamp. I liked the overall design of the app, but the gameplay was rather barebones and had some unresolved bugs.</p>
<P>* For this capstone project, I did think very hard about what kind of game I wanted to make in Java. I really would like to make an Adventure game with a great story and artwork, but given the time alloted, I think that will have to be a project for the future. So, I thought maybe I could try to make another Tetris game in Java and hopefully make it much more feature-rich and responsive than my previous attempt in Javascript, while still keeping some of the same art, textures, and themes I used last time.</p>
<P>* I ended up researching SEVERAL different 3rd party libraries for building games. LibGDX did look beneficial with its HTML compatibility, making it easier to bring into a React app. However, I also looked into Java Swing/JPanel and JavaFX. I have decided to try and build my game using JavaFX for Open SDK 11 (Corretto). This is because I reasearched some very interesting examples of different animation effects and transitions you can use that are visually impressive with minimal code to get set up.</p>
<P>* I have read on forums that JavaFX can have a steep learning curve, but I also found some tutorials that look like they will walk me through getting my basic JavaFX project set up and beginning to build my game UI.</p>
<P>* I have also found some tutorials on creating puzzle games in JavaFX that I might watch for inspiration or just to get familiar with common classes in this library.</p>
<P>* I will now create my Architecture overview UML using GenMyModel that will list the possible classes and inheritors for this project. Please note that this document is just an estimation of the classes and features I will be implementing, but this architecture may change if I have issues delivering the Minimum Viable Product. Features that are lowest priority (wont affect grade) are HighScore saves and Game Sounds and Music. I would ideally love to get all of these features in my final program, but I'll need to focus on the MVP first before going wild with extras.</p>
<h2>
    3. Executing the Plan
</h2>
<P>* After finishing my classes Architecture diagram, I then followed the guide linked below by Tim Buchalka for setting up a JavaFX Project for JDK 11 Using IntelliJ. So glad I found this video series because it was a perfect introduction to making my first app in JavaFX. https://www.youtube.com/watch?v=WtOgoomDewo</p>

<P>* I also did some additional setup and troubleshooting by following the instructions provided in this resource: http://tutorials.jenkov.com/javafx/your-first-javafx-application.html/</p>

<P>* My REQUIRED ABSTRACT CLASS will belong to my "AbstractShape" class and the other shapes will inherit from it. The abstract shape will have common methods that will run for all shape types (rotate, fall, reverse, clear, etc.).</p>
<P>* My REQUIRED INTERFACEs are actually pulled directly from JavaFX and I will implement them from my Highscores and SoundController classes.</p>
<P>* My REQUIRED STREAM function I wrote when I encountered an issue with game slowdown when new pieces were falling. This was happening because my RandomPiece method was having to load every time a piece dropped and it was slowing down the Run() thread. So, I wrote a stream function to generate 50 random, abstract pieces when the game loads or when the level changes. A second stream function then Collects that array list of abstract pieces, shuffles their piece types, and returns them as proper Shapes (or Tetrominos as they are called in Tetris). This stream completely eliminates my game slowdown issue because it's not having to run the randomPiece method every few seconds.</p>

<P>* After the initial setup and poking around in JavaFX, I began creating my packages and Classes in my project. I added some placeholder methods and even borrowed some refactored code from my Javascript Tetris game for things like piece rotation and grid generation. These may or may not need to be rewritten, but it's worth a shot to try.</p>

<P>* I continued writing logic and some sudocode when I didn't want to get bogged down in Syntax. When I eventually got stuck and needed some guidance on popular JavaFX classes to use for games like this, I found the following guide for making a very basic Tetris game in JavaFX. https://www.youtube.com/watch?v=boAJUSN8fOU&t=9s</p>
<P>* This guide was great for seeing just a simple implementation of Tetris looked like in JavaFX. I followed some parts of the guide to finish writing certain methods I was blocked on getting to work myself. This guide was helpful, but as stated, it is a VERY barebones implementation of the game and I want to add several more advanced features on my own if time allows.</p>
<P>* After getting my logic written and classes set up, I needed to connect the UI and animate the pieces. I used this resource to look up Animations and found some really cool transition effects I'd like to also use for my levels. https://www.tutorialspoint.com/javafx/javafx_animations.htm</p>
<P>* I was able to get animated pieces loading, falling, and rotating as requried after a lot of troubleshooting. I then moved to adding level transition effects that trigger when the player's score reaches certain thresholds. I used this article as a resource: https://thecodinginterface.com/blog/javafx-animated-scene-transitions/</p>
<P>* After several very late nights, the basic features of the game were working. I now could move on to styling the UI, adding more visual flare, and adding my advanced features. I used JavaFX's built in styling to customize colors and background displays. http://www.java2s.com/Code/Java/JavaFX/SetLabelTextcolor.htm</p>
<P>* I looked up some guides on how to save game scores in JavaFX and ended up using the BufferedReader and BufferedWriter Java Classes for my high score saves. I wrote a small Highscore display Modal pop-up using JavaFX classes and then moved on to setting up the saving and loading of a user's .txt file, saved locally. https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html</p>
<P>* I then followed a few tutorials on setting up sound for JavaFX applications. I used some music I had saved from my previous Tetris game in Javascript to load. I also ripped 8-bit sounds from the original Tetris Game Boy game from a rom file and converted them to .wav format for compatibility. It took a while and a lot of trial and error, but I eventually got sounds and music working. Still need to add mute and volume controls to the UI if time allows. http://www.java2s.com/Code/Java/JavaFX/SetLabelTextcolor.htm
https://stackoverflow.com/questions/23202272/how-to-play-sounds-with-javafx</p>
<P>* My final steps over the weekend will be to continue testing the application and fixing bugs, commenting my code and refactoring for readability, and possibly importing the game into a React app. I will need to do more research on this to see how much time it might take.</p>

<h2>
    4. Reflection / Refactor
</h2>
<P>* It was definitely a struggle learning an all new library for this project. But I wanted to set my goals high, since I was already familiar with creating text-only games in the console. I am very thankful for all the great resources I found online and for users on stackoverflow.com answering some puzzling issues I was having with certain JavaFX features.</P>
<P>* Looking back, I can't believe how many lines of code it took to create this application and flesh out all of the features. I definitely need to separate my GameUI class into GameUI and LogicUI classes at some point because it is currently too large. I intended to do this in my Archetecture UML, but getting my UI to work properly meant just writing a lot of my frontend logic directly alongside my UI for JavaFX.</P>
<P>* My code is still very bloated with repitition and bandaid solutions to bugs I encountered along the way. There were certain things in my UI that I had to write duplicate code for to get them to update when certain game methods were called. I fully intend to refactor this bloated code, but my limited knowlege about JavaFX means I will have to do some further reserach to accomplish this. For now, I'm trying to comment my code thouroughly and rename any methods or variables that aren't clear to their purpose.</P>
*
*
*
*
*
*