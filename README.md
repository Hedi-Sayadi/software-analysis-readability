# Readability Analysis

This repository contains a Java project for analysing the readability of
Java code snippets. It extracts a small set of code metrics and uses the
resulting data to classify snippets according to their readability.

The project was developed as part of the Software Analysis course at the
University of Passau.

## What it does

The application has two commands:

- `preprocess` reads `.jsnp` snippet files, calculates readability-related
  metrics, and writes the results to a CSV file.
- `classify` reads the generated CSV data and evaluates a classifier using
  the readability labels in the dataset.

The available metrics are:

- `LINES` - number of lines
- `TOKEN_ENTROPY` - token entropy
- `H_VOLUME` - Halstead volume
- `CYCLOMATIC_COMPLEXITY` - cyclomatic complexity

## Requirements

- Java 21
- Maven

## Build and test

From the project directory, run:

```bash
mvn clean test
```

To create the executable JAR:

```bash
mvn package
```

The JAR is created in `target/`.

## Usage

After building the project, preprocess the example dataset with:

```bash
java -jar target/Readability-Analysis-1.0.jar preprocess ^
  --source resources/snippets ^
  --ground-truth resources/truth_scores.csv ^
  --target target/features.csv ^
  LINES TOKEN_ENTROPY H_VOLUME CYCLOMATIC_COMPLEXITY
```

On macOS or Linux, replace the Windows line-continuation character (`^`)
with a backslash (`\`), or run the command on one line.

The generated CSV can then be evaluated with:

```bash
java -jar target/Readability-Analysis-1.0.jar classify ^
  --data target/features.csv
```

The classification command prints summary evaluation results, including
accuracy, area under the ROC curve, and F-score.

## Project structure

```text
src/          Main application and metric implementations
test/         Unit tests
resources/    Example snippets and readability scores
pom.xml       Maven build configuration
```

Generated files such as `target/` and local IDE files are intentionally
excluded from version control.
