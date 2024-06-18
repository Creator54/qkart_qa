## Introduction
The project covers different scenarios to validate the functionality and behavior of the E-Commerce application.

## Test Cases
<details>
<summary><b>Test Case 1: Verify User Registration and Login | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that a new user can successfully register and login with valid credentials.
- **Steps**:
  1. Navigate to the user Registration page of QKart
  2. Enter the username and password
  3. Enter the confirm password
  4. Click on the Register button
  5. Navigate to the Login page
  6. Login with the the newly created user credentials

</details>
<details>
<summary>Screenshots</summary>

![TestCase01_Login_Passed.png](./screenshots/TestCase01_Login_Passed.png)

![TestCase01_Registration_Passed.png](./screenshots/TestCase01_Registration_Passed.png)

![TestCase01_StartTestCase.png](./screenshots/TestCase01_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 2: Verify if user Reregistration using the same username is not allowed | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that the registration process prevents using an existing username.
- **Steps**:
  1. Navigate to the Registration page of QKart
  2. Enter the user name of an existing user
  3. Enter password and confirm password
  4. Click on the Register button

</details>
<details>
<summary>Screenshots</summary>

![TestCase02_Reregistration_Blocked.png](./screenshots/TestCase02_Reregistration_Blocked.png)

![TestCase02_StartTestCase.png](./screenshots/TestCase02_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 3: Verify functionality of the search box | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that All results displayed on the page should contain the name 'yonex'', There should be a 'No products found' message
- **Steps**:
  1. Navigate to home page
  2. Search for text : 'yonex' in the search box
  3. Ensure that the results shown contain the search text in their name
  4. Search for text: 'Gesundheit'
  5. Ensure that no products found message is displayed

</details>
<details>
<summary>Screenshots</summary>

![TestCase03_StartTestCase.png](./screenshots/TestCase03_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 4: Verify the presence of Size Chart | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: Size chart Link should be present, On Click  the size chart should be displayed, The contents of the size chart are correct, The Size Selection drop down must be present for items with size chart, Each size present in the size selection dropdown must have corresponding entry on the size chart
- **Steps**:
  1. Navigate to home page
  2. Search for text: 'UNIFACTOR Mens Running Shoes'
  3. Verify that the Size Chart Link Exists
  4. Click on the size chart link and check the contents of the size chart
  5. Verify the existence of size selection drop down
  6. Ensure each size present in the size selection dropdown has corresponding reference on the size chart

</details>
<details>
<summary>Screenshots</summary>

![TestCase04_SizeChart_Closed.png](./screenshots/TestCase04_SizeChart_Closed.png)

![TestCase04_SizeChart_Opened.png](./screenshots/TestCase04_SizeChart_Opened.png)

![TestCase04_SizeChart_Presence.png](./screenshots/TestCase04_SizeChart_Presence.png)

![TestCase04_SizeChart_Validated.png](./screenshots/TestCase04_SizeChart_Validated.png)

![TestCase04_StartTestCase.png](./screenshots/TestCase04_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 5: Happy Flow Test Completed | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify the The user is re-directed to the checkout page, The contents of the cart on the checkout page should be correct, The user should be redirect to the order success page, Order successful message should be displayed
- **Steps**:
  1. Register a new user
  2. Login using this new user
  3. Add the following products in to the cart 'YONEX Smash Badminton Racquet x 1', 'Tan Leatherette Weekender Duffle x1'
  4. Click on Checkout
  5. Add a new address
  6. Select the added address using radio button
  7. Click on Place order
  8 . Ensure that the order is placed

</details>
<details>
<summary>Screenshots</summary>

![TestCase05_HappyFlow_Verified.png](./screenshots/TestCase05_HappyFlow_Verified.png)

![TestCase05_StartTestCase.png](./screenshots/TestCase05_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 6: Verify that cart can be edited | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that the The Cart on the checkout page should contain the following items 'The Minimalist Slim Leather Watch x1'
- **Steps**:
  1. Navigate to the home page
  2. Add the follwing products to the cart 'The Minimalist Slim Leather Watch x2', 'Bonsai Spirit Tree Table Lamp x1'
  3. Remove 1 Qty of The Minimalist Slim Leather Watch from the cart
  4. Remove 1 Qty of Bonsai Spirit Tree Table Lamp from the cart
  5. Click on Checkout
  6. Verify the contents of cart on the checkout page
  7. Log out

</details>
<details>
<summary>Screenshots</summary>

![TestCase06_Cart_Edit_Verified.png](./screenshots/TestCase06_Cart_Edit_Verified.png)

![TestCase06_StartTestCase.png](./screenshots/TestCase06_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 7: Verify that cart contents are persisted after logout | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that the The Checkout button must be present as there are items already added to the cart, The contents of the cart must be 'Stylecon 9 Seater RHS Sofa Set x1', 'The Minimalist Slim Leather Watch x3'
- **Steps**:
1. Register a new user
2. Login using the registered user
3. Add the following items in the cart: 'Stylecon 9 Seater RHS Sofa Set x1', 'The Minimalist Slim Leather Watch x3'
4. Logout
5. Login using the same user
6. Verify the existence of checkout button
7. Verify the existence of the items added to cart

</details>
<details>
<summary>Screenshots</summary>

![TestCase07_Cart_Contents_Persisted.png](./screenshots/TestCase07_Cart_Contents_Persisted.png)

![TestCase07_StartTestCase.png](./screenshots/TestCase07_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that an insufficient balance error is thrown when the wallet balance is not enough to place an order.
- **Steps**:
1. Register a new user
2. Login using the registered user
3. Add the following items in the cart 'Stylecon 9 Seater RHS Sofa Set x10'
4. Check out
5. Add a new address
6. Select the added address using radio button
7. Click on Place order
8. Verify if the order is placed

</details>
<details>
<summary>Screenshots</summary>

![TestCase08_Insufficient_Balance_Error.png](./screenshots/TestCase08_Insufficient_Balance_Error.png)

![TestCase08_StartTestCase.png](./screenshots/TestCase08_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 9: Verify that product added to cart is available when a new tab is opened | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that a product added to the cart is available when a new tab is opened.
- **Steps**:
1. Register a new user
2. Login with the newly created user
3. Search for th e product 'YONEX Smash Badminton Racquet''
4. Add the above mentioned product to the cart
5. Open a new tab and go to the QKART home page
6. Check if the product added in step 4 is present in the cart

</details>
<details>
<summary>Screenshots</summary>

![TestCase09_EndTestCase.png](./screenshots/TestCase09_EndTestCase.png)

![TestCase09_StartTestCase.png](./screenshots/TestCase09_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that the Privacy Policy and About Us pages are displayed correctly.
- **Steps**:
1. Navigate to QKART page
2. Click on the Privacy Policy Link
3. Click on the about us Link

</details>
<details>
<summary>Screenshots</summary>

![TestCase10_EndTestCase.png](./screenshots/TestCase10_EndTestCase.png)

![TestCase10_StartTestCase.png](./screenshots/TestCase10_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 11: Verify that contact us option is working correctly  | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that the Contact Us option is working correctly.
- **Steps**:
1. Navigate to QKART page
2. Click on the contact us link
3. Update the contact us details
4. Click on close

</details>
<details>
<summary>Screenshots</summary>

![TestCase11_EndTestCase.png](./screenshots/TestCase11_EndTestCase.png)

![TestCase11_StartTestCase.png](./screenshots/TestCase11_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 12:  Ensure that the links on the QKART advertisement are clickable | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To ensure that the links on the QKART advertisement are clickable.
- **Steps**:
1. Navigate to QKART page
2. Register a new user
3. Login using the registered user
4. Search for product: 'YONEX Smash Badminton Racquet'
5. Buy the product
6. In the last page, check if the advertisement links are clickable

</details>
<details>
<summary>Screenshots</summary>

![TestCase12_EndTestCase.png](./screenshots/TestCase12_EndTestCase.png)

![TestCase12_StartTestCase.png](./screenshots/TestCase12_StartTestCase.png)

</details>
</ul>
</details>

## Results

12 out of 12 test cases Passed
