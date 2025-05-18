:- initialization(main).

% Facts
disease(flu, [cold, cough, dizziness, fever, headache, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([non_smoker, smoker, junk_food, sedentary]), travel_history(hiking), diet([junk_food, unbalanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(95-100)]).
disease(dengue, [fever, low_platelet, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([non_smoker, smoker]), travel_history(tropical), diet([balanced, unbalanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(95-100)]).
disease(measles, [rash, fever, cough, conjunctivitis, runny_nose, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([shared_clothes, non_smoker]), travel_history(none), diet([balanced, unbalanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(95-100)]).
disease(malaria, [fever, chills, headache, nausea, vomiting, muscle_pain, fatigue, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([non_smoker]), travel_history(tropical), diet([balanced, unbalanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(95-100)]).
disease(tuberculosis, [persistent_cough, chest_pain, coughing_up_blood, fatigue, weight_loss, fever, night_sweats, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([smoker]), travel_history(none), diet([unbalanced, unbalanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(95-100)]).
disease(covid19, [fever, cough, shortness_of_breath, fatigue, muscle_pain, loss_of_taste_or_smell, sore_throat, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([non_smoker]), travel_history(recent), diet([balanced, unbalanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(95-100)]).
disease(hypertension, [headache, dizziness, blurred_vision, chest_pain, shortness_of_breath, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([smoker]), travel_history(none), diet([unbalanced, unbalanced]), bp(120-180), heart_rate(60-100), cholesterol(200-300), blood_sugar(70-140), oxygen_saturation(95-100)]).
disease(diabetes, [increased_thirst, frequent_urination, extreme_hunger, unexplained_weight_loss, fatigue, blurred_vision, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(diabetes), lifestyle([non_smoker]), travel_history(none), diet([high_carbs]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(140-200), oxygen_saturation(95-100)]).
disease(asthma, [shortness_of_breath, chest_tightness, wheezing, coughing, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(asthma), lifestyle([non_smoker]), travel_history(none), diet([balanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(90-100)]). 
disease(pneumonia, [cough, fever, chills, shortness_of_breath, chest_pain, fatigue, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([non_smoker]), travel_history(none), diet([balanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(90-100)]). 
disease(hepatitis_b, [fatigue, nausea, vomiting, abdominal_pain, dark_urine, jaundice, age(0-100), gender(any), weight(10-120), height(50-200), medical_history(none), lifestyle([non_smoker]), travel_history(none), diet([balanced]), bp(90-120), heart_rate(60-100), cholesterol(150-200), blood_sugar(70-140), oxygen_saturation(95-100)]).

% Predicate to diagnose disease based on symptoms and patient history
diagnose(Disease, SymptomsAndHistory) :- 
    disease(Disease, DiseaseSymptomsAndHistory), 
    subset(SymptomsAndHistory, DiseaseSymptomsAndHistory).

% Predicate to check if all elements of one list are in another list
subset([], _).
subset([age(Age)|T], List) :- 
    member(age(Min-Max), List), 
    Age >= Min, Age =< Max, 
    subset(T, List).
subset([gender(Gender)|T], List) :- 
    (member(gender(Gender), List); member(gender(any), List)), 
    subset(T, List).
subset([weight(Weight)|T], List) :- 
    member(weight(Min-Max), List), 
    Weight >= Min, Weight =< Max, 
    subset(T, List).
subset([height(Height)|T], List) :- 
    member(height(Min-Max), List), 
    Height >= Min, Height =< Max, 
    subset(T, List).
subset([medical_history(History)|T], List) :- 
    member(medical_history(History), List), 
    subset(T, List).
subset([lifestyle(Lifestyle)|T], List) :- 
    member(lifestyle(L), List), 
    subset(Lifestyle, L), 
    subset(T, List).
subset([travel_history(Travel)|T], List) :- 
    member(travel_history(Travel), List), 
    subset(T, List).
subset([diet(Diet)|T], List) :- 
    member(diet(D), List), 
    subset(Diet, D), 
    subset(T, List).
subset([bp(BP)|T], List) :- 
    member(bp(Min-Max), List), 
    BP >= Min, BP =< Max, 
    subset(T, List).
subset([heart_rate(HeartRate)|T], List) :- 
    member(heart_rate(Min-Max), List), 
    HeartRate >= Min, HeartRate =< Max, 
    subset(T, List).
subset([cholesterol(Cholesterol)|T], List) :- 
    member(cholesterol(Min-Max), List), 
    Cholesterol >= Min, Cholesterol =< Max, 
    subset(T, List).
subset([blood_sugar(BloodSugar)|T], List) :- 
    member(blood_sugar(Min-Max), List), 
    BloodSugar >= Min, BloodSugar =< Max, 
    subset(T, List).
subset([oxygen_saturation(OxygenSaturation)|T], List) :- 
    member(oxygen_saturation(Min-Max), List), 
    OxygenSaturation >= Min, OxygenSaturation =< Max, 
    subset(T, List).
subset([H|T], List) :- 
    member(H, List), 
    subset(T, List).

% Main entry point
main :- 
    (diagnose(Disease, [fever, low_platelet, age(20), gender(any), weight(75), height(175), medical_history(none), lifestyle([non_smoker]), travel_history(tropical), diet([unbalanced]), bp(110), heart_rate(80), cholesterol(180), blood_sugar(100), oxygen_saturation(98)]) -> 
    write('Disease: '), write(Disease), nl ; 
    write('No diagnosis. '), nl).