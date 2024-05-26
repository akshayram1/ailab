% Male and Female relationships
male(varad).
father(prashant, varad).
mother(sarika, varad).
female(sunanda).
female(manjusha).
female(kavita).
female(madhu).
female(shruti).
female(gauri).
female(riya).
female(shreya).
female(jui).
female(munni).
female(shraddha).
female(swapna).
female(nirmala).
female(sia).

% Grandparent relationships
grandfather(purushuttam, varad).
grandfather(chandrakant, varad).
grandmother(sunanda, varad).
grandmother(nirmala, varad).

% Aunt and Uncle relationships
aunt(manjusha, varad).
aunt(kavita, varad).
aunt(madhu, varad).
aunt(munni, varad).
aunt(shraddha, varad).
aunt(swapna, varad).
uncle(shekhar, varad).
uncle(mayur, varad).
uncle(ankush, varad).
uncle(jai, varad).
uncle(nikhil, varad).
uncle(karan, varad).

% Cousin relationships
cousin(shruti, varad).
cousin(gauri, varad).
cousin(riya, varad).
cousin(shreya, varad).
cousin(jui, varad).
cousin(aniket, varad).
cousin(nimish, varad).
cousin(arnav, varad).

% Third aunt's family
husband(nikhil, shreya).
husband(karan, jui).
child(sia, shreya).

% Parent relationships
parent(prashant, varad).
parent(sarika, varad).

% Niece relationships
niece(sia, varad).

% Sibling relationships
sibling(shruti, gauri).
sibling(jui, shreya).

% Marital relationships
married(prashant, sarika).
married(nikhil, shreya).
married(karan, jui).
married(shekhar, manjusha).
married(shekhar, madhu).
married(ankush, shraddha).
married(jai, swapna).
married(munni, mayur).

% Additional rules for family relationships
mother_of(X, Y) :- mother(X, Y).
father_of(X, Y) :- father(X, Y).
sister_of(X, Y) :- female(X), sibling(X, Y).
grandparent_of(X, Y) :- (grandfather(X, Y); grandmother(X, Y)).
aunt_of(X, Y) :- female(X), (sister_of(X, Z), parent_of(Z, Y); married(X, Z), sibling(Z, W), parent_of(W, Y)).
uncle_of(X, Y) :- male(X), (brother_of(X, Z), parent_of(Z, Y); married(X, Z), sibling(Z, W), parent_of(W, Y)).
cousin_of(X, Y) :-(aunt_of(Z, Y); uncle_of(Z, Y)), parent(Z, X), X \= Y.