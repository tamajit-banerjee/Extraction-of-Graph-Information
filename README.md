# Extraction-of-Graph-Information

A java based implementation that maps data about comic book characters (Marvel, Naruto etc) to a graph and uses an effective method for extracting significant information from the data part of the graph, including the graph components.

## Part of Assignment 4 of COL106 course 2020.

## Problem Statement 

Write a Java program ​, that takes three command line arguments: two csv files , and a function name.

Implement the information in the two csv files as a graph and write functions to output the following:

### (a) Function: average. 

Should print the average number of characters each Marvel character is associated with, as a float upto two decimal places 

Sample random output:
~~~
$java solution nodes.csv edges.csv average

7.43

~~~

### (b) Function: rank. 

Should print a sorted list of all characters, with comma as delimiter (only comma, as delimiter and no space). Sorting should be in descending order of co-occurrence with other characters. That is, characters with more co-occurrences appear before. If there is a tie between characters based on co-occurrence count, then the order should be descending based on lexicographic order of the character strings. 


Sample random output:


~~~
$java solution nodes.csv edges.csv rank

Yogish,Riju,Rahul

~~~


### (c) Function: independent_storylines_dfs. 

Should implement DFS, then find independent storylines, that have no edge across them, using DFS. Print the characters in each independent storyline, as a separate line in the output. The largest storyline (with maximum characters) should appear at the top, followed by the second largest and so on. Within each line, the character names should be delimited with comma, and lexicographically sorted in descending order. If two storylines have same number of characters, ties should be broken in lexicographically descending order of character names. 

Sample random output:

~~~
$java solution nodes.csv edges.csv independent_storylines_dfs

Riju,Rahul
Yogish

~~~


## Structure:

Marvel characters graph

nodes.csv 327 nodes: Each node represents a character.

edges.csv 9891 edges (with weights): Edges between nodes/characters represents that the characters/nodes have appeared together. Edge-weights are proportional to the number of co-occurrences.



