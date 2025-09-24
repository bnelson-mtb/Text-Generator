# Text Generator

This is a Java-based text generator that uses a Markov chain-like model to generate new text from a given input file.

## Description

This program reads an input text file and builds a statistical model of the text. It records the frequency of each word and the frequency of the words that immediately follow it. It can then generate new text starting from a "seed" word, using one of three generation modes.

## Features

*   **Deterministic Mode:** Generates text by always choosing the most frequent word to follow the current word.
*   **Random Mode:** Generates text by choosing the next word based on a weighted probability of the words that follow the current word.
*   **Probable Mode:** Returns a list of the most probable words to follow a given seed word.

## How to Run

1.  Compile the Java files.
2.  Run the `TextGenerator` class from the command line with the following arguments:

    ```
    java comprehensive.TextGenerator <input_file> <seed_word> <k> <mode>
    ```

    *   `<input_file>`: The path to the text file to use for building the text generation library.
    *   `<seed_word>`: The word to start the text generation from.
    *   `<k>`: The number of words to generate.
    *   `<mode>`: The generation mode. Can be `deterministic`, `random`, or `probable`.

    For example:

    ```
    java comprehensive.TextGenerator distinct_1000.txt the 100 random
    ```

## Project Structure

*   `src/comprehensive/`: Contains the core logic for the text generator.
    *   `TextGenerator.java`: The main class for running the text generator.
    *   `Generator.java`: The class that handles building the text generation library and generating new text.
    *   `WordEntry.java`: A helper class for storing information about each word in the library.
*   `src/timing/`: Contains classes for running performance experiments on the text generator.
*   `*.txt`: Text files used for building the text generation library and for the timing experiments.

## Timing Experiments

The `src/timing/` directory contains classes for running performance experiments on the text generator. These experiments measure the time it takes to perform certain operations, such as building the text generation library or generating new text, as various parameters are changed.

To run a timing experiment, compile and run the desired experiment class from the `src/comprehensive/` directory. For example:

```
java comprehensive.NextKWordsTimingExperiment
```

## Authors

*   Kent Wilkison
*   Brady Nelson
