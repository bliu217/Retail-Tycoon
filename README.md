# My Personal Project
*Bernard Liu - Phase 0 Proposal*

## Brief Description of Program

The program I want to create is going to be a store managing simulator game. The premise
of this game is that you are running some general store. The tasks you will perform will include:
- scanning customer items
- making bargains with customers if they want a discount (random)
- learning the cashier system ui (this will probably be implemented in later phases)
- perform some admin tasks (purchasing and checking inventory etc.)

The point of this game is to achieve a high score, you do this by getting through as
many customers in the day, making successful bargains, and making it as far as possible
without going negative in profit. As soon as you go negative the game ends. 

## Target Audience

Anyone that wants to play the game.

## Why this Interests Me

I was a cashier before and I think I can make a realistic simulator. Also, I have worked with
pretty terrible cashier systems and am convinced I can make a better one
than the company I worked for (in terms of user interface).

## User Stories

- As a user, I want to check out the items of the customer
- As a user, I want to be able to see a list of my high scores
- As a user, I want to purchase goods for my store and manage inventory
- As a user, I want to see my score and store balance
- As a user, I want to have the option to save my in-game progress when I close the application
- As a user, I want to have the option to load a new game or load from the previous save

## Instructions for Grader

- To generate the first action (adding Items to Cashier inventory), first click "New Game", enter a save
name, click "STORE", and press the green plus buttons to add some items to cart, then click purchase.
- To generate the second action (viewing Items added to Cashier inventory), exit the "STORE" by pressing the
"BACK" button, then clicking the purple "INVENTORY" button to see the items.
- The visual component can viewed when you start the game ("RETAIL TYCOON" logo, as well as the copyright message
at the bottom)
- The option to load previously saved data is in the main menu when you press "Load Game"
- The option to save current data is in the game between transactions, a green "SAVE" button located in the top
right.

## Phase 4 Task 2

Example of log output:

Thu Apr 04 23:45:31 PDT 2024\
Added Banana to Bernard's inventory.\
Thu Apr 04 23:45:38 PDT 2024\
Bernard sold a Notebook

## Phase 4 Task 3

If I had more time to work on this project, I would most likely follow the SRP principle more in my UI classes.
For example, even though each class represents only one scene, it has many components that could be split up into
different classes. This is apparent in the MainMenu class, where I could split the buttons and user info display into
two different classes. I could also refactor the customer's cart panel into it's separate class because it has
a completely different function compared to the rest of the class. As for my StoreDisplay and ItemHolder, I could
implement the Observers pattern since there are 3 things that update visually every time a plus or minus button is 
pressed. That being said, I would split the 2 elements in the StoreDisplay(totalPanel, balanceBox + balance) into 
separate classes which would each listen to the itemHolders present in the StoreDisplay. I might also introduce a
Counter class to add to ItemHolder, which would also listen to the buttons and have the responsibilities of the qtyPanel. 
