#!/usr/bin/python3
# -*- coding: utf-8 -*-
import random
import typing

AMOUNT_OF_RECORDS = 50
RESULT_FILE = "sample.csv"

class Passport:
    number: int
    age: int
    month: int
    salary: int # RUB
    trips: int
    
    def __init__(self, number: int, age: int):
        self.number = number
        self.age = age
        return
    
    def representation(self):
        return f"{self.number},{self.month},{self.salary},{self.age},{self.trips}\n"

def passport_number():
    return random.randint(10000, 99999)

def month():
    return random.randint(1, 12)

def salary():
    return random.randint(20_000, 200_000)

def age():
    return random.randint(18, 70)

def trips():
    return random.randint(0, 10)


if __name__ == "__main__":
    print("Generating...")
    passports = []
    for i in range(0, AMOUNT_OF_RECORDS):
        passport = Passport(passport_number(), age())
        passport.month = month()
        passport.salary = salary()
        passport.trips = trips()
        passports.append(passport)
    
    with open(RESULT_FILE, "w") as file:
        for item in passports:
            file.write(item.representation())
        
    print("Done")

