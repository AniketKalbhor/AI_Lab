:- initialization(main).

% Symptoms 
symptom(leaf_spots). 
symptom(yellow_leaves). 
symptom(wilting). 
symptom(powdery_substance). 
symptom(leaf_curling). 

% Environmental Conditions 
environment(high_humidity). 
environment(poor_airflow). 
environment(overwatering). 

% Knowledge Base
has_symptom(tulsi, powdery_substance). 
has_symptom(tulsi, leaf_spots). 
has_symptom(rose, wilting). 
has_symptom(rose, yellow_leaves). 
has_symptom(mango, leaf_curling). 
has_symptom(mango, yellow_leaves). 
has_symptom(basil, leaf_spots). 
has_symptom(basil, yellow_leaves). 
has_environment(tulsi, high_humidity). 
has_environment(rose, overwatering). 
has_environment(basil, poor_airflow). 

% Disease Rules 
at_risk_of_fungal_disease(Plant) :- 
    has_symptom(Plant, powdery_substance), 
    has_symptom(Plant, leaf_spots), 
    has_environment(Plant, high_humidity). 

at_risk_of_bacterial_disease(Plant) :- 
    has_symptom(Plant, wilting), 
    has_symptom(Plant, yellow_leaves), 
    has_environment(Plant, overwatering). 

at_risk_of_viral_disease(Plant) :- 
    has_symptom(Plant, leaf_curling), 
    has_symptom(Plant, yellow_leaves), 
    \+ has_environment(Plant, overwatering). 

% Diagnostic Predicate 
check_plant_disease(Plant) :- 
    at_risk_of_fungal_disease(Plant), 
    write(Plant), write(' is at risk of fungal disease.'), nl. 

check_plant_disease(Plant) :- 
    at_risk_of_bacterial_disease(Plant), 
    write(Plant), write(' is at risk of bacterial disease.'), nl. 

check_plant_disease(Plant) :- 
    at_risk_of_viral_disease(Plant), 
    write(Plant), write(' is at risk of viral disease.'), nl. 

check_plant_disease(Plant) :- 
    \+ at_risk_of_fungal_disease(Plant), 
    \+ at_risk_of_bacterial_disease(Plant), 
    \+ at_risk_of_viral_disease(Plant), 
    write(Plant), write(' shows no sign of serious disease.'), nl.

main :- 
    check_plant_disease(tulsi), 
    check_plant_disease(rose), 
    check_plant_disease(mango), 
    check_plant_disease(basil).

# Explanation of prolog_env.pl
# Problem Statement:
# This Prolog program is designed to diagnose plant diseases based on observed symptoms and environmental conditions. It analyzes various plants (tulsi, rose, mango, basil) to determine if they are at risk of fungal, bacterial, or viral diseases by examining their symptoms and the environmental conditions they're exposed to.
# Theory:
# 🔹 Knowledge Representation: The program uses facts and rules to represent knowledge about plant diseases, symptoms, and environmental conditions.
# 🔹 Diagnostic Logic: The diagnosis works through logical inference, where specific combinations of symptoms and environmental factors lead to different disease classifications.
# 🔹 Negation as Failure: The program uses negation (+) to establish that certain conditions are not present, which affects disease diagnosis.
# Explanation of functions used:
# The program uses Prolog predicates (functions) to establish relationships between plants, symptoms, environmental conditions, and diseases:

# has_symptom/2: Defines which symptoms a plant exhibits
# has_environment/2: Defines which environmental conditions a plant is exposed to
# at_risk_of_fungal_disease/1: Determines if a plant is at risk of fungal disease
# at_risk_of_bacterial_disease/1: Determines if a plant is at risk of bacterial disease
# at_risk_of_viral_disease/1: Determines if a plant is at risk of viral disease
# check_plant_disease/1: Main diagnostic predicate that evaluates all disease risks and outputs results

# Basic explanation of general approach:
# The program follows a rule-based expert system approach:

# Define facts about symptoms and environmental conditions
# Establish which plants have which symptoms and environmental conditions
# Create rules that determine disease risk based on combinations of symptoms and conditions
# Apply these rules to each plant to diagnose potential diseases
# Output the diagnostic results

# Variables Used:
# 🔹 Plant: Represents the plant being diagnosed (tulsi, rose, mango, basil)
# 🔹 Symptoms:

# leaf_spots
# yellow_leaves
# wilting
# powdery_substance
# leaf_curling
# 🔹 Environmental Conditions:
# high_humidity
# poor_airflow
# overwatering

# Flow of Execution:

# The program initializes with the main predicate
# The main predicate calls check_plant_disease for each plant (tulsi, rose, mango, basil)
# For each plant, the program checks:

# First, if it meets the criteria for fungal disease
# If not, checks for bacterial disease
# If not, checks for viral disease
# If none of the above, declares the plant shows no sign of serious disease


# The diagnosis results are printed for each plant

# State Space Tree:
# main
# ├── check_plant_disease(tulsi)
# │   └── at_risk_of_fungal_disease(tulsi)
# │       ├── has_symptom(tulsi, powdery_substance) ✓
# │       ├── has_symptom(tulsi, leaf_spots) ✓
# │       └── has_environment(tulsi, high_humidity) ✓
# │       → "tulsi is at risk of fungal disease."
# │
# ├── check_plant_disease(rose)
# │   ├── at_risk_of_fungal_disease(rose) ✗
# │   └── at_risk_of_bacterial_disease(rose)
# │       ├── has_symptom(rose, wilting) ✓
# │       ├── has_symptom(rose, yellow_leaves) ✓
# │       └── has_environment(rose, overwatering) ✓
# │       → "rose is at risk of bacterial disease."
# │
# ├── check_plant_disease(mango)
# │   ├── at_risk_of_fungal_disease(mango) ✗
# │   ├── at_risk_of_bacterial_disease(mango) ✗
# │   └── at_risk_of_viral_disease(mango)
# │       ├── has_symptom(mango, leaf_curling) ✓
# │       ├── has_symptom(mango, yellow_leaves) ✓
# │       └── \+ has_environment(mango, overwatering) ✓
# │       → "mango is at risk of viral disease."
# │
# └── check_plant_disease(basil)
#     ├── at_risk_of_fungal_disease(basil) ✗
#     ├── at_risk_of_bacterial_disease(basil) ✗
#     ├── at_risk_of_viral_disease(basil) ✗
#     └── "basil shows no sign of serious disease."
# Disease Rules Explained:

# Fungal Disease Risk: A plant is at risk if it shows powdery substance, leaf spots, and is in high humidity.
# Bacterial Disease Risk: A plant is at risk if it shows wilting, yellow leaves, and is being overwatered.
# Viral Disease Risk: A plant is at risk if it shows leaf curling, yellow leaves, and is NOT being overwatered.
# No Disease Risk: A plant is considered healthy if it doesn't meet any of the above criteria.

# This expert system demonstrates how logical rules can be used to create a diagnostic tool for plant diseases based on observable symptoms and environmental factors, showcasing Prolog's strength in rule-based knowledge representation and inference.
