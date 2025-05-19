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

# Explanation of prolog_career.pl
# Problem Statement
# The prolog_career.pl program implements a career guidance system that recommends potential career paths based on a user's interests. It matches the user's inputs against a knowledge base of interests and their corresponding career categories to provide personalized career suggestions.
# Theory
# 🔹 Approach: Uses declarative logic programming to match user interests with predefined categories and provide career recommendations.
# 🔹 Constraints: Recommendations are limited to the predefined career paths and interest mappings in the knowledge base.
# 🔹 Input: User-provided list of interests (e.g., [coding, painting, fitness])
# 🔹 Output: Career recommendation based on matched interests (e.g., "Software Developer or Data Scientist")
# ⚙️ Functions Explained

# intersection/3: A helper predicate that finds common elements between two lists. This is critical for matching user interests against predefined interest categories.
# career_guidance/0: The main entry point that welcomes users, collects their interests, calls find_career to determine suitable careers, and displays the results.
# find_career/2: The core logic function that matches user interests with predefined categories and returns appropriate career recommendations.
# start/0: A wrapper function that initiates the career_guidance process.

# 📊 Variables Used

# Interests: The user-provided list of interests (e.g., [coding, painting, fitness])
# InterestList: Predefined lists of interests for each category
# Category: The career category that matches the user's interests (e.g., technology, arts)
# Career: The recommended career path based on the matched category
# Matches: Common elements between user interests and predefined interest lists

# 🔁 Flow of Execution

# Program starts with the initialization directive, which calls the start predicate
# start/0 calls career_guidance/0
# career_guidance/0 displays welcome message and prompts for interests
# User inputs their interests as a Prolog list
# find_career/2 is called to match interests with career categories:

# For each interest_category fact in the knowledge base
# Calculate intersection of user interests and category interests
# If intersection is non-empty, a match is found
# Retrieve corresponding career for the matched category


# If a match is found, display the career recommendation
# If no match is found, display an apology message

# Short State Space Tree
# start
# └── career_guidance
#     ├── Display welcome message
#     ├── Read user interests
#     └── find_career
#         ├── Match 1: interest_category([sports, fitness], sports)
#         │   ├── Calculate intersection with user interests
#         │   └── If non-empty → career(sports, "Professional Athlete or Sports Coach")
#         ├── Match 2: interest_category([drawing, painting, design], arts)
#         │   ├── Calculate intersection with user interests
#         │   └── If non-empty → career(arts, "Graphic Designer or Fine Artist")
#         ├── Match 3: interest_category([coding, technology, problem_solving], technology)
#         │   ├── Calculate intersection with user interests
#         │   └── If non-empty → career(technology, "Software Developer or Data Scientist")
#         └── ... (other career categories)
# The program elegantly demonstrates Prolog's strength in knowledge representation and logical inference. It uses pattern matching and logical rules to create a simple yet effective career recommendation system based on matching user interests with predefined categories.
