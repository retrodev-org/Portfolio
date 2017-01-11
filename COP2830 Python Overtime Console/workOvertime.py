__author__ = 'James Walker Holland'

# Get input
checkingBalance = float(input("What is the balance of your checking account? "))
billsDue = float(input("How much do you need to pay your bills this month? "))
nextPaycheck = float(input("How much do you expect on your next paycheck? "))
overtimeAvailable = (input("Is overtime available? ").lower() == "y")

# Decision time
if checkingBalance < 0:
    if overtimeAvailable == True:
        print("Your checking balance is negative.")
        print("Yes, you should work overtime. You're broke.")
    else:
        print("Your checking balance is negative.")
        print("Sadly, overtime is not available.")
        print("You can't even afford groceries. I hope you've stocked up on ramen.")
elif (billsDue >= checkingBalance) and (billsDue < (checkingBalance + nextPaycheck)):
    if overtimeAvailable == "y":
        print("You could go without working OT this time.")
    else:
        print("Too bad there's no OT available. It would let you get ahead.")
    print("You owe more than you have, but your next check will fix it.")
elif billsDue >= (checkingBalance + nextPaycheck):
    if overtimeAvailable == "y":
        print("You owe more than you have, even including your next paycheck.")
        print("Yes, you should work overtime. Also, I hope you like ramen.")
    else:
        print("You need to work on this budgeting thing.")
        print("There's no OT, and you're going to be in the negative.")
        print("You're going to eat ramen for a long time.")
else:
    print("You're doing pretty good. You'll have money left, not even counting the next check.")
    if overtimeAvailable == "y":
        print("You don't need to work OT, but it never hurts.")
    else:
        print("There's no OT available. Good thing you don't need it.")
