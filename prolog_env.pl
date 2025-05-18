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