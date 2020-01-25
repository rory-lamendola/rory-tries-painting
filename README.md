# PaintShop

## Overview
You own a paint factory. There are N different colors you can mix, and each color can be prepared "matte" or "glossy". So, you can make 2N different types of paint. Each of your customers has a set of paint types they like, and they will be satisfied if you have at least one of those types prepared. At most one of the types a customer likes will be a "matte". You want to make N batches of paint, so that there is exactly one batch for each color of paint, and it is either matte or glossy. For each customer, you make at least one paint type that they like. The minimum possible number of batches are matte (since matte is more expensive to make). Find whether it is possible to satisfy all your customers given these constraints, and if it is, what paint types you should make. If it is possible to satisfy all your customers, there will be only one answer which minimizes the number of matte batches.

## Algorithm 
I wanted to use a greedy implementation of a set cover algorithm to solve this problem.  The basic idea here is that we have a particular universe U comprised of n elements.  Then we have a collection of subsets of U where every subset has an associated cost. We want to find a minimum cost subcollection of subsets that covers all n elements of U.

In this example, our elements are the customers of the paint shop, and the subsets that cover these customers are the paints.  Each paint has an associated cost (matte or glossy).  

We start off with an empty set cover.  From here, we greedily add a new set (paint) that has the best cost effectiveness.  In this case, it means we want to take the paint that gives us the most satisfied customers for the lowest cost (ie. glossy).  We iterate on this until we reach a solution, or we run out of paints.  Greedy algorithms don't always produce the most optimal solution, but oh well, I already work for this company.

The problem with this method is the constraint of only having one *type* of each shade of paint (matte or glossy).  I wanted to strictly try this out with only the set cover algorithm, instead of implementing some kind of backtracking logic. This constraint kind of ruins the set cover algorithm, and makes it not a particularly fun solution.  So I threw the constraint out (*shrug*).  Frankly, if a paint store refuses to make a paint for a customer because they happen to have used that shade before, they don't deserve a good solution.  That's a terrible business move.

You can read more about set covers [here](https://www.geeksforgeeks.org/set-cover-problem-set-1-greedy-approximate-algorithm/)
