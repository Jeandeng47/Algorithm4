#!/usr/bin/env python3
import os
import pandas as pd
import matplotlib.pyplot as plt

# To run this script using isolated environment (on macOS):
# cd Chapter_1-5
# python3 -m venv .venv
# source .venv/bin/active
# pip install pandas matplotlibd
# python3 plot.py
# deactivate


# get dir
script_dir = os.path.dirname(os.path.abspath(__file__))
output_dir = script_dir 

files = [
    ("Quick-find.csv", "Quick-Find"),
    ("Quick-union.csv", "Quick-Union"),
    ("Weighted-quick-union.csv", "Weighted-Quick-Union"),
]

for fname, title in files:
    # read csv files
    path = os.path.join(script_dir, fname)
    df = pd.read_csv(path)

    ops = df["op"]
    costCon = df["costCon"]
    avgCon  = df["avgCon"]
    costU   = df["costUnion"]
    avgU    = df["avgUnion"]

    # create 2 plot
    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 6), sharex=True)
    fig.suptitle(f"{title} Amortized Costs", fontsize=14)

    # connected() cost
    ax1.scatter(ops, costCon, s=2, color="gray", label="costConn")
    ax1.plot(ops, avgCon,  color="red",  linewidth=1, label="avgConn")
    ax1.set_ylabel("array accesses")
    ax1.legend(loc="upper right")

    # union() cost
    ax2.scatter(ops, costU, s=2, color="gray", label="costUnion")
    ax2.plot(ops, avgU,  color="red",  linewidth=1, label="avgUnion")
    ax2.set_xlabel("operation #")
    ax2.set_ylabel("array accesses")
    ax2.legend(loc="upper right")

    plt.tight_layout(rect=[0, 0, 1, 0.95])

    # save images
    save_name = title.lower().replace(" ", "_") + ".png"
    save_path = os.path.join(output_dir, save_name)
    plt.savefig(save_path, dpi=150)
    print(f"Saved plot to {save_path}")

    plt.show()
