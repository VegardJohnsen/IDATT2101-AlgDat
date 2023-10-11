import random
import timeit

# Number of days/ n
num_of_days = 100000

# Change the variables below to get different runtimes
num_of_executions = 100  # Number of executions of the algorithm/ method

# Could also change the variation width
min_price_change = -10
max_price_change = 10


# duration generates testdata and calculates the duration
def duration():
    test_data = [random.randint(min_price_change, max_price_change) for _ in range(num_of_days)]

    def run_algo_one_time():
        max_profit(test_data)

    elapsed_time = timeit.timeit(run_algo_one_time, number=num_of_executions) / num_of_executions
    return elapsed_time


# max_profit iterates through a list of changes in stock-price and returns optimal buy- and sell-day
def max_profit(relative_changes):
    profit = 0
    current_profit = 0
    buy_day = 0
    sell_day = 0
    current_buy_day = 0

    for i, change in enumerate(relative_changes):
        current_profit += change

        if current_profit < 0:
            current_profit = 0
            current_buy_day = i + 1

        if current_profit > profit:
            profit = current_profit
            buy_day = current_buy_day
            sell_day = i + 1

    return buy_day, sell_day


print(duration())
