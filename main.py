import timeit

# Defining n and x
n_value = 13
x_value = 2.5

executions = 100


# method_1 add x, n times
def method_1(n, x):
    if n == 1:
        return x
    sum1 = method_1(n - 1, x) + x
    return sum1


# method_2 add x, n times
def method_2(n, x):
    if n == 1:
        return x
    if n % 2 == 0:
        sum1 = method_2(n / 2, x + x)
    else:
        sum1 = method_2((n - 1) / 2, x + x) + x
    return sum1


# run_time measures the efficiency of the calculations
def run_time(n, x):

    def run_method_1():
        method_1(n, x)

    def run_method_2():
        method_2(n, x)
    run_time_1 = (timeit.timeit(run_method_1, number=executions) / executions) * 1000
    run_time_2 = (timeit.timeit(run_method_2, number=executions) / executions) * 1000
    return run_time_1, run_time_2


# Prints a structured result
print("Metode 1: ")
print(method_1(n_value, x_value))
print("Metode 2: ")
print(method_2(n_value, x_value))
print("Tidsbruk metode 1 og 2 " + "(s): ")
print(run_time(n_value, x_value))
