# jetbrains-table-editor

Table editor with formula support

Patterns:

- Observer Pattern: to notifying when cell is changed and then rerendering corresponding cells.
- Builder Pattern: for optional and several parameters of CellModel
- Singleton Pattern: Observable class should be singleton for reliability
- Strategy Pattern: for calculating the different formulas. This pattern makes code flexible for example implementation a new formula is easier with this pattern.


Error Handling:

- Mutual reference: Loop references are prevented such as A1 -> C1 and C1 -> A1
- Invalid formula
- Invalid input
- Division by zero
- Invalid reference: If reference does not exist.
- Self reference: cell can not refer itself.

Features:
- Reference: User can type references to formula bar. Example: =A1+2
- Formula Support: User can type formulas to formula bar. Example =pow(A1,2) + 42
- When user clicks to the cell the formula bar will be updated. If cell has formula then formula bar will show formula otherwise it will show the cell value.
- The cell will be updated whenever reference of the cell is updated.
- When the cell is changed, only affected cells will be rerendered not whole table.

Missings:
- Program prevents mutual references but It does not look for diamond references such as: A1 -> B1, B1 -> C1, C1 -> A1. 
- I was going to implement Depth First Search algorithm for checking the reference is valid but I could not find time for this.
- I could not complete my unit tests, also I could not implement UI tests.
