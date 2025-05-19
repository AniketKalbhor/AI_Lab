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

# Explanation of prolog_disease.pl
# Problem Statement:
# The prolog_disease.pl code implements a medical diagnostic system using Prolog's logical programming paradigm. The system aims to diagnose diseases based on a patient's symptoms, medical history, and various physiological parameters. The program uses a facts-and-rules approach to determine potential diagnoses for patients with specific symptom profiles and health attributes.
# Theory:
# 🔹 The program uses a knowledge base of diseases with their associated symptoms and patient characteristics.
# 🔹 It implements logical inference to match patient symptoms against known disease profiles.
# 🔹 The system utilizes Prolog's pattern matching and backtracking to find all possible diagnoses.
# Constraints:
# 🔹 Disease diagnosis requires matching symptoms and patient data against predefined disease patterns.
# 🔹 The system can only diagnose diseases that are explicitly defined in its knowledge base.
# 🔹 Numerical ranges (age, weight, etc.) must fall within specified limits for each disease.
# Input:
# 🔹 Patient symptoms (e.g., fever, cough)
# 🔹 Patient demographic data (age, gender)
# 🔹 Physical characteristics (weight, height)
# 🔹 Medical history information
# 🔹 Lifestyle factors (diet, smoking status)
# 🔹 Travel history
# 🔹 Vital signs (blood pressure, heart rate, etc.)
# Output:
# 🔹 Disease diagnosis for the patient based on the provided symptoms and characteristics.
# ⚙️ Functions Explained

# diagnose/2: The main predicate that matches patient symptoms and history against disease profiles. It uses the subset predicate to check if patient symptoms are a subset of disease symptoms.
# subset/2: A recursive predicate that checks if all elements from one list are contained in another list. This has multiple versions to handle different types of data (numerical ranges, categorical values, etc.).
# main/0: The entry point of the program that tests a specific case (a patient with fever, low platelet count, etc.) and outputs the diagnosis.

# 📊 Variables Used

# Disease: Represents the diagnosed condition (flu, dengue, etc.)
# SymptomsAndHistory: List of patient symptoms and characteristics to be matched against disease profiles
# DiseaseSymptomsAndHistory: List of symptoms and characteristics associated with a specific disease
# Numerical ranges (e.g., age(0-100), bp(90-120)): Represent acceptable ranges for physical parameters
# Categorical values (e.g., gender(any), travel_history(tropical)): Represent discrete characteristics

# 🔁 Flow of Execution

# The program starts with the main predicate, which calls diagnose with a specific set of patient characteristics.
# The diagnose predicate attempts to match the patient data against each disease in the knowledge base.
# For each disease, the subset predicate checks if the patient's symptoms and characteristics are compatible with the disease profile.
# The subset predicate handles different data types differently:

# For numerical ranges (age, weight, etc.), it checks if the patient's value falls within the disease's acceptable range.
# For categorical data (gender, medical history), it checks for exact matches or if the disease accepts any value.
# For list-based data (lifestyle, diet), it checks if the patient's values are a subset of acceptable values.


# If all checks pass, the disease is identified as a match and returned as the diagnosis.
# The result is then printed to the console.

# Short State Space Tree:
# main
# |
# └── diagnose(Disease, [fever, low_platelet, age(20), ...])
#     |
#     ├── Disease = flu
#     |   └── subset([fever, low_platelet, ...], [cold, cough, ...]) → FAIL
#     |
#     ├── Disease = dengue
#     |   └── subset([fever, low_platelet, ...], [fever, low_platelet, ...]) → SUCCESS
#     |   └── OUTPUT: "Disease: dengue"
#     |
#     ├── Disease = measles
#     |   └── subset([fever, low_platelet, ...], [rash, fever, ...]) → FAIL
#     |
#     └── ... (other diseases) → FAIL
# In this particular example, the system diagnoses the patient with dengue fever based on their symptoms and characteristics. The diagnosis succeeds because the patient's profile (fever, low platelet count, tropical travel history, etc.) matches the criteria for dengue fever defined in the knowledge base.
