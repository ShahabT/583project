# Query Optimization by Analyzing and Pre-caching Frequent Queries in Compile-Time

** Course project for Advanced Compilers (EECS583) **


The `src/` folder contains the codes for out library (under `src/queryManager/`) and benchmarks (under `src/benchmark/`).
All parts are written in java. We have created scripts for making easy to use with the tool:

+ To simply compile and run a application use `./javac.sh appName <parameters to run>`.
    It will use the installed `javac` command in your system.
+ To optimize your queries with our tool use our "SQL Pre-Fetcher Compiler" command: `./sqlpfc.sh appName <parameters to run>`.
    This script will profile and analyze the queries and it will inject our sql pre-fetcher into your code.
    The output would be the class files of the `.class` files with injected pre-fetcher.
    You can optionally give a last parameter `run` to the command, and it will run you app after optimization.
+ To run the experiments you can simply use the `run_github_expr.sh` and `run_yelp_expr.sh` script.
    They will take care of everything and will save the results in the `expr-result` folder.
    Before being able to run the experiments you'll need to set up the datasets.
    The instructions to do so are available in `README` under the folder of each application in `src/benchmark/`.