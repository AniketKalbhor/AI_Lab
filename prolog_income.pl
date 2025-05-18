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