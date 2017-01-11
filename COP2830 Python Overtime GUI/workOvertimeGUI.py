__author__ = 'James Walker Holland'

# Work overtime scenario with tkinter GUI

from tkinter import *
import ctypes  # Enables dialog box to display output message


def makeDecision():
    balance      = float(balanceText.get())
    billAmount   = float(billsText.get())
    checkAmount  = float(checkText.get())
    outputMessage = StringVar()

    if balance < 0:
        if otAvailable:
            outputMessage = "Your checking balance is negative. Yes, you should work overtime. You're broke."
        else:
            outputMessage = "Your checking balance is negative. Sadly, overtime is not available. \nYou can't even afford groceries. I hope you've stocked up on ramen."
    elif (billAmount >= balance) and (billAmount < (balance + checkAmount)):
        if otAvailable:
            outputMessage = "You could go without working OT this time."
        else:
            outputMessage = "Too bad there's no OT available. It would let you get ahead. \nYou owe more than you have, but your next check will fix it."
    elif billAmount >= (balance + checkAmount):
        if otAvailable:
            outputMessage = "You owe more than you have, even including your next paycheck. \nYes, you should work overtime. Also, I hope you like ramen."
        else:
            outputMessage = "You need to work on this budgeting thing. There's no OT, and \nyou're going to be in the negative. You're going to eat ramen for a long time."
    else:

        if otAvailable:
            outputMessage = "You don't need to work OT, but it never hurts."
        else:
            outputMessage = "There's no OT available. Good thing you don't need it."

    ctypes.windll.user32.MessageBoxW(0, outputMessage, "Decision is...", 1)


Window         = Tk()
Window.geometry("400x300+100+200")
Window.title("Overtime Advisor")

balanceText    = StringVar()
billsText      = StringVar()
checkText      = StringVar()
otAvailable    = BooleanVar()

labelWelcome   = Label(Window, text='Welcome to the Overtime Advisor\n          ').grid(row=0, column=1)
labelBalance   = Label(Window, text='What is your checking account balance?     ').grid(row=1, column=1)
labelBills     = Label(Window, text='What is the total of your bills this month?').grid(row=2, column=1)
labelNextCheck = Label(Window, text='What is the amount of your next check?     ').grid(row=3, column=1)
labelOTAvail   = Label(Window, text='Is overtime available?                     ').grid(row=4, column=1)

balanceAmount  = Entry(Window,textvariable=balanceText).grid(row=1, column=2)
billAmount     = Entry(Window,textvariable=billsText).grid(row=2, column=2)
checkAmount    = Entry(Window,textvariable=checkText).grid(row=3, column=2)
overtimeCheck  = Checkbutton(Window, text="Yes", variable=otAvailable, onvalue=1, offvalue=0).grid(row=4, column=2)

decideButton   = Button(Window,text='Should I work overtime?',command=(makeDecision)).grid(row=5, column=1)

Window.mainloop()

