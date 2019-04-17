# An Analysis of the Kirkman Schoolgirl Problem

An upcoming analysis of the Kirkman Schoolgirl Problem. Includes different algorithms, complexity and attempts at an n-schoolgirl solution.

This analysis is in memory of Phil Dorin, a phenomenal mentor with a love of the craft. When introducing me to this problem, he told me with great enthusiasm:

> 8 queens is to Kirkman as amateur is to professional.

## The Problem

In 1850, Thomas Kirkman proposed the following problem:

> Fifteen young ladies in a school walk out three abreast for seven days in succession: it is required to arrange them daily so that no two shall walk twice abreast.

Suppose we had 15 schoolgirls labeled `A`, `B`, `C`, ..., `O`. Then we may give one possible solution:
| Sun | Mon | Tue | Wed | Thu | Fri | Sat |
|-----|-----|-----|-----|-----|-----|-----|
| ABC | ADG | AKN | AHL | AEM | AFO | AIJ |
| DEF | BKM | BDH | BJO | BFI | BEL | BGN |
| GHI | CIO | CFJ | CDM | CGL | CHN | CEK |
| JKL | EHJ | EGO | FGK | DJN | DIK | DLO |
| MNO | FLN | ILM | EIN | HKO | GJM | FHM |

## Interest in the Problem

TODO

## Notes on Backtracking

TODO

## Constraints

TODO

## Runtime Results

TODO

## Versions Included

* `SchoolgirlSolver_042018.java`
  * This is my first attempt to solve the problem in my Algorithms class with Dr. Dorin in Spring 2018--apologies in advance for the poor coding standard as it was my second semester of doing programming. Some "magic numbers" are used given the nature of the problem.