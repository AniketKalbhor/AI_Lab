% Helper predicate: Find intersection of two lists
intersection([], _, []).
intersection([H|T], List2, [H|Result]) :-
    member(H, List2),  % Check if H is in List2
    intersection(T, List2, Result).
intersection([_|T], List2, Result) :-
    intersection(T, List2, Result).

% Knowledge Base

% Career paths based on interests and skills
career(sports, "Professional Athlete or Sports Coach").
career(arts, "Graphic Designer or Fine Artist").
career(technology, "Software Developer or Data Scientist").
career(healthcare, "Doctor or Nurse").
career(business, "Entrepreneur or Business Analyst").
career(law, "Lawyer or Legal Consultant").
career(science, "Research Scientist or Lab Technician").
career(education, "Teacher or Educational Consultant").
career(writing, "Author or Content Writer").
career(music, "Musician or Music Producer").
career(engineering, "Mechanical, Civil, or Software Engineer").
career(higher_studies, "Researcher or Academician pursuing advanced degrees").
career(dance, "Choreographer or Dance Instructor").


% Matching interests to categories
interest_category([sports, fitness], sports).
interest_category([drawing, painting, design], arts).
interest_category([coding, technology, problem_solving], technology).
interest_category([medicine, helping_others, biology], healthcare).
interest_category([management, leadership, finance], business).
interest_category([justice, debating, critical_thinking], law).
interest_category([experiments, curiosity, research], science).
interest_category([teaching, mentoring, learning], education).
interest_category([writing, reading, storytelling], writing).
interest_category([music, singing, composing], music).
interest_category([engineering, machines, innovation], engineering).
interest_category([studies, research, academia, knowledge], higher_studies).
interest_category([dancing,choreography], dance).


% Main Predicate
career_guidance :-
    write('Welcome to the Career Guidance System!'), nl,
    write('Please list your interests as a Prolog list, e.g., [coding, painting, fitness]: '), nl,
    read(Interests),
    (   find_career(Interests, Career)
    ->  format('Based on your interests, a potential career path could be: ~s~n', [Career])
    ;   write('Sorry, we could not determine a suitable career for your interests.'), nl
    ).

% Helper Predicate: Find the career based on interests
find_career(Interests, Career) :-
    interest_category(InterestList, Category),
    intersection(Interests, InterestList, Matches),
    Matches \= [],  % Ensure there is at least one match
    career(Category, Career).

% Entry point
start :-
    career_guidance.

% Initialization directive
:- initialization(start).

# Prolog Career Guidance System Explanation
# Problem Statement:
# The prolog_income.pl program is a career guidance system that recommends potential career paths based on a user's interests. The system matches the user's input interests with predefined categories and suggests appropriate career options based on the matches found.
# Explanation of functions used:
# The program uses several Prolog predicates (functions) to implement the recommendation system. The main functions include intersection checking between lists, career-interest matching, and user interaction logic.
# Basic explanation of general approach:
# The system works by maintaining a knowledge base of career paths and their associated interest categories. When a user inputs their interests, the system finds the intersection between the user's interests and predefined interest categories. It then recommends careers based on matching categories with at least one interest overlap.
# Explanation of variables:

# Interest lists: Collections of interests associated with specific career categories
# Categories: Career domains like sports, arts, technology, etc.
# Careers: Specific job recommendations for each category
# User interests: The list of interests provided by the user

# Flow of execution:

# The program initializes and welcomes the user
# It prompts the user to enter their interests as a Prolog list
# The system reads the user's input
# It searches the knowledge base for matching interest categories
# It identifies careers associated with matching categories
# It outputs career recommendations or an apology if no match is found

# Theory:
# 🔹 Knowledge Representation: The program uses fact-based knowledge representation with:

# Facts about careers associated with categories
# Facts about interests associated with categories
# Logic rules to match user interests with career paths

# 🔹 List Processing: The program leverages Prolog's list processing capabilities to:

# Find intersections between lists
# Match user interests with predefined categories
# Handle variable-length interest lists

# 🔹 Pattern Matching: The system uses Prolog's pattern matching for:

# Identifying matching interest categories
# Retrieving associated career recommendations
# Filtering out non-matching options

# ⚙️ Functions Explained:

# intersection/3: Finds common elements between two lists
# interest_category/2: Maps lists of interests to career categories
# career/2: Associates career categories with job recommendations
# career_guidance/0: Main interaction function that handles user I/O
# find_career/2: Core logic to match interests to careers
# start/0: Entry point that initiates the program

# 📊 Variables Used:

# Interests: User-provided list of interests
# InterestList: Predefined list of interests for a category
# Category: Career domain (sports, arts, etc.)
# Career: Specific job recommendation
# Matches: Intersection between user interests and category interests

# 🔁 Flow of Execution:

# Program starts → initialization(start)
# start/0 calls → career_guidance/0
# User enters interests
# find_career/2 processes input
# intersection/3 identifies matching interests
# If matches exist → retrieve and display career
# If no matches → display apology message

# The system serves as a simple expert system that demonstrates how logical programming can be used to create recommendation systems based on interest matching and predefined knowledge bases.RetryClaude can make mistakes. Please double-check responses.
