## Introduction
The project covers different scenarios to validate the functionality and behavior of the E-Commerce application.

## Test Cases
<details>
<summary><b>Test Case 1: Verify User Registration and Login | PASS</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that a new user can successfully register with valid credentials.
- **Steps**:
  1. Navigate to the registration page.
  2. Enter valid user details (username, password, etc.).
  3. Click on the registration button.
  4. Verify if the registration is successful and the user is redirected to the home page.
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
  1. Navigate to the registration page.
  2. Enter an existing username and valid credentials.
  3. Click on the registration button.
  4. Verify if the registration fails and displays an appropriate error message.
</details>
<details>
<summary>Screenshots</summary>

![TestCase02_Reregistration_Blocked.png](./screenshots/TestCase02_Reregistration_Blocked.png)

![TestCase02_StartTestCase.png](./screenshots/TestCase02_StartTestCase.png)

</details>
</ul>
</details>

<details>
<summary><b>Test Case 3: Verify functionality of the search box | FAIL</b></summary>
<ul>
<details>
<summary>Details</summary>

- **Objective**: To verify that a registered user can login with valid credentials.
- **Steps**:
  1. Navigate to the login page.
  2. Enter valid username and password.
  3. Click on the login button.
  4. Verify if the login is successful and the user is redirected to the home page.
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

- **Objective**: To verify that the login process fails with invalid credentials.
- **Steps**:
  1. Navigate to the login page.
  2. Enter invalid username and password.
  3. Click on the login button.
  4. Verify if the login fails and an appropriate error message is displayed.
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

- **Objective**: To verify the happy flow of buying products from the website.
- **Steps**:
  1. Register a new user.
  2. Login with the newly registered user's credentials.
  3. Add products to the cart.
  4. Go to the checkout page and place the order.
  5. Verify if the order is placed successfully.
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

- **Objective**: To verify that the cart can be edited by adding/removing products.
- **Steps**:
  1. Register a new user.
  2. Login with the newly registered user's credentials.
  3. Add products to the cart.
  4. Update the quantity of products in the cart.
  5. Verify if the cart is updated accordingly.
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

- **Objective**: To verify that the cart contents are persisted even after the user logs out.
- **Steps**:
  1. Register a new user.
  2. Login with the newly registered user's credentials.
  3. Add products to the cart.
  4. Log out the user.
  5. Log in again with the same user's credentials.
  6. Verify if the cart contents are still present.
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
  1. Register a new user.
  2. Login with the newly registered user's credentials.
  3. Add products to the cart with a high quantity.
  4. Go to the checkout page and place the order.
  5. Verify if the insufficient balance error message is displayed.
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
  1. Register a new user.
  2. Login with the newly registered user's credentials.
  3. Add a product to the cart.
  4. Open a new tab and navigate to the website.
  5. Verify if the cart contents are still present.
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
  1. Register a new user.
  2. Login with the newly registered user's credentials.
  3. Click on the Privacy Policy link.
  4. Verify if the Privacy Policy page is displayed correctly.
  5. Click on the About Us link.
  6. Verify if the About Us page is displayed correctly.
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
  1. Navigate to the Contact Us page.
  2. Enter name, email, and message.
  3. Click on the Contact Us button.
  4. Verify if the Contact Us form submission is successful.
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
  1. Register a new user.
  2. Login with the newly registered user's credentials.
  3. Add a product to the cart.
  4. Go to the checkout page and place the order.
  5. Verify if the QKART advertisements are displayed.
  6. Verify if the links on the QKART advertisements are clickable.
</details>
<details>
<summary>Screenshots</summary>

![TestCase12_EndTestCase.png](./screenshots/TestCase12_EndTestCase.png)

![TestCase12_StartTestCase.png](./screenshots/TestCase12_StartTestCase.png)

</details>
</ul>
</details>

