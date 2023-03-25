Introduction: 
Computer systems supporting multiprogramming or multitasking execute multiple program tasks 
concurrently. One of the objectives of multiprogramming is to maximize resource utilization which is 
achieved by sharing system resources amongst multiple users and system processes. Efficient resource 
sharing depends on the efficient scheduling of competing processes. As the processor is the most 
important resource, CPU scheduling becomes very important in achieving the system design goals. 
Scheduling usually refers to selecting which process to run next - but can also refer to which 
input/output (or disk) operation to do next. Many algorithms have been designed to implement CPU 
scheduling. Design methods include analytic modeling, deterministic modeling, and simulations. 
Simulation being the most accurate of all is commonly employed for a system’s performance 
evaluation despite the fact that it requires complex programming for developing an efficient simulator.
CPU Scheduler is part of the operating system that allocates CPU resources to processes. Process or 
program requires CPU and other resources to run. When the process is running, it changes its state 
like below.

1. New: the new process is created
2. Running: instructions in the process are executed
3. Waiting: the process is waiting for events (ex I/O completion) to happen
4. Ready: the process is waiting for the CPU to be allocated
5. Terminated: CPU execution is finished.
 
This project deals with 4 Algorithms for scheduling which are: 
• First-Come, First-Served (FCFS) Scheduling 
• Shortest-Job-First (SJF) Scheduling 
• Priority Scheduling (Non-Preemptive)
• Round Robin (RR) Scheduling

These algorithms are non-preemptive algorithms are designed so that once a process enters the 
running state, it cannot be preempted (the control can’t be taken away from that process) until it 
completes its time, whereas preemptive scheduling is based on priority where a scheduler may 
preempt (the control can be taken away from that process) a low priority running process anytime 
when a high priority process enters into a ready queue.

FCFS: FCFS (non-preemptive) Algorithm, which schedule processes by their arrival time, working as a 
First in First Out "FIFO" Queue, it's easy to implement and understand, but the bad thing about it is the 
high waiting time for the processes, where each process needs to wait for the whole current running 
process to finish, which also increases the total turnaround time.

SJF: SJF could be a preemptive or non-preemptive scheduling algorithm, it's much better than FCFS in 
lowering the wait time, The processer should know in advance how much time the process will take. 
or otherwise, the schedular can't know which process takes less time to select.

Priority: Priority may be a preemptive or non-preemptive algorithm and one of the most common 
scheduling algorithms in batch systems, each process is assigned a priority. The process with the 
highest priority is to be executed first and so on. Processes with the same priority are executed on a 
first come first served basis.

Round Robin: Round Robin is a preemptive process scheduling algorithm, where each process is 
provided with a fixed time to execute, it is called a quantum (Time Quantum), Once a process is 
executed for a given time period, it is preempted and another process executes for a given time period, 
and Context switching is used to save states of the preempted process
