# USE CASES

USE CASE DIAGRAM: https://drive.google.com/file/d/0B1kWV1hOcvR2OWlmbDZyOUhsTVU/view?usp=sharing
SEQUENCE DIAGRAM FOR USE CASES: https://drive.google.com/open?id=0B1kWV1hOcvR2OWlmbDZyOUhsTVU
CLASS DIAGRAM: https://drive.google.com/file/d/0B7_RGl5LAUiuOU1MS3hmNlBMVGM/view?usp=sharing

#### Go to www.draw.io to open file

1. Manager  
  - Open store 
  - Close store  
  - Add/remove products  (by text file) 

2. Customer 
  - Add/removes items from an order 
  - Finalizes + pays an order 
  - See catalog  

3. Store 
  - Authenticate customer VS manager 
  - Holds and updates catalog (Data structure -> HashMap) 
  - Output an invoice after transaction by customer  
  - Outputs to transaction.txt to keep list of all transactions 
  
4. POST - UI 
