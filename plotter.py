from matplotlib import pyplot as plt
import random
import timeit

from time_complexity import max_profit

# Change the variables below to get different runtimes
num_of_executions = 100  # Number of executions of the algorithm/ method
max_numb_of_days = 1000  # Max number of days/stock price data

# Could also change the variation width
min_price_change = -10
max_price_change = 10

execution_times = []
numb_of_days = []


# get_days_and_execution_times return a list with number of days.
def get_days_and_execution_times():
    for days in range(1, max_numb_of_days + 1, 10):
        test_data = [random.randint(min_price_change, max_price_change) for _ in range(days)]

        def run_algo():
            max_profit(test_data)

        elapsed_time_algo = timeit.timeit(run_algo, number=num_of_executions) / num_of_executions
        execution_times.append(elapsed_time_algo)
        numb_of_days.append(days)
    return numb_of_days, execution_times


def create_scatter():
    days, duration = get_days_and_execution_times()
    fig, ax = plt.subplots()  # Create a figure and axis
    ax.scatter(days, duration, marker='o', color='blue')  # Use the axis to create the scatter plot
    ax.set_xlabel('Number of Days')  # Set the x-axis label
    ax.set_ylabel('Execution Time (seconds)')  # Set the y-axis label
    ax.set_title('Algorithm Execution Time vs Number of Days')  # Set the plot title
    ax.grid(True)  # Add grid lines
    plt.savefig('days_vs_duration.png')  # Save the plot
    plt.show()  # Show the plot


create_scatter()
