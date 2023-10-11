import matplotlib.pyplot as plt
from main import run_time

n_values = []

for i in range(1, 30):
    n_values.append(i)


x_value = 2.5


def plot_scatter(n, x):
    plt.figure(figsize=(10, 5))

    for n_value in n:
        time_1, time_2 = run_time(n_value, x)
        plt.scatter(n_value, time_1, color='b')
        plt.scatter(n_value, time_2, color='r')

    plt.xlabel('n')
    plt.ylabel('Time (s)')
    plt.title('Run Time of Method 1 and Method 2')
    plt.legend(['Method 1', 'Method 2'])
    plt.savefig('plot.png')
    plt.show()

plot_scatter(n_values, x_value)
