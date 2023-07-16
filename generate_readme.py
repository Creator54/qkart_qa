#!/usr/bin/env python

import os
import re

def generate_testcase_entry(testcase_number, testcase_name, description):
    entry = f"<details>\n<summary>TestCase{testcase_number:02d}: {testcase_name}</summary>\n"
    entry += "<details>\n<summary>Details</summary>\n\n"
    entry += f"{description}\n</details>\n"
    entry += "<details>\n<summary>Screenshots</summary>\n\n"

    # Get the list of screenshots for the current test case
    screenshots = [f for f in os.listdir("screenshots") if re.match(f"TestCase{testcase_number:02d}_.*\.png", f)]

    # Sort the screenshots to ensure they are added in the correct order
    screenshots.sort()

    # Add the screenshots to the entry with relative paths
    for screenshot in screenshots:
        entry += f"![{screenshot}]('./screenshots/{screenshot}')\n\n"

    entry += "</details>\n</details>\n\n"
    return entry


def generate_readme():
    readme_content = """\
## Introduction
The project covers different scenarios to validate the functionality and behavior of the E-Commerce application.

## Test Cases
"""
    # Add test case entries
    test_cases = [
        # Test case number, Test case name, Test case description
        (1, "Verify User Registration with valid credentials",
         "- **Objective**: To verify that a new user can successfully register with valid credentials.\n"
         "- **Steps**:\n"
         "  1. Navigate to the registration page.\n"
         "  2. Enter valid user details (username, password, etc.).\n"
         "  3. Click on the registration button.\n"
         "  4. Verify if the registration is successful and the user is redirected to the home page."
         ),
        (2, "Verify User Registration with an existing username",
         "- **Objective**: To verify that the registration process prevents using an existing username.\n"
         "- **Steps**:\n"
         "  1. Navigate to the registration page.\n"
         "  2. Enter an existing username and valid credentials.\n"
         "  3. Click on the registration button.\n"
         "  4. Verify if the registration fails and displays an appropriate error message."
         ),
        (3, "Verify Login with valid credentials",
         "- **Objective**: To verify that a registered user can login with valid credentials.\n"
         "- **Steps**:\n"
         "  1. Navigate to the login page.\n"
         "  2. Enter valid username and password.\n"
         "  3. Click on the login button.\n"
         "  4. Verify if the login is successful and the user is redirected to the home page."
         ),
        (4, "Verify Login with invalid credentials",
         "- **Objective**: To verify that the login process fails with invalid credentials.\n"
         "- **Steps**:\n"
         "  1. Navigate to the login page.\n"
         "  2. Enter invalid username and password.\n"
         "  3. Click on the login button.\n"
         "  4. Verify if the login fails and an appropriate error message is displayed."
         ),
        (5, "Verify Happy Flow of buying products",
         "- **Objective**: To verify the happy flow of buying products from the website.\n"
         "- **Steps**:\n"
         "  1. Register a new user.\n"
         "  2. Login with the newly registered user's credentials.\n"
         "  3. Add products to the cart.\n"
         "  4. Go to the checkout page and place the order.\n"
         "  5. Verify if the order is placed successfully."
         ),
        (6, "Verify that cart can be edited",
         "- **Objective**: To verify that the cart can be edited by adding/removing products.\n"
         "- **Steps**:\n"
         "  1. Register a new user.\n"
         "  2. Login with the newly registered user's credentials.\n"
         "  3. Add products to the cart.\n"
         "  4. Update the quantity of products in the cart.\n"
         "  5. Verify if the cart is updated accordingly."
         ),
        (7, "Verify that cart contents are persisted after logout",
         "- **Objective**: To verify that the cart contents are persisted even after the user logs out.\n"
         "- **Steps**:\n"
         "  1. Register a new user.\n"
         "  2. Login with the newly registered user's credentials.\n"
         "  3. Add products to the cart.\n"
         "  4. Log out the user.\n"
         "  5. Log in again with the same user's credentials.\n"
         "  6. Verify if the cart contents are still present."
         ),
        (8, "Verify that insufficient balance error is thrown when the wallet balance is not enough",
         "- **Objective**: To verify that an insufficient balance error is thrown when the wallet balance is not enough to place an order.\n"
         "- **Steps**:\n"
         "  1. Register a new user.\n"
         "  2. Login with the newly registered user's credentials.\n"
         "  3. Add products to the cart with a high quantity.\n"
         "  4. Go to the checkout page and place the order.\n"
         "  5. Verify if the insufficient balance error message is displayed."
         ),
        (9, "Verify that product added to cart is available when a new tab is opened",
         "- **Objective**: To verify that a product added to the cart is available when a new tab is opened.\n"
         "- **Steps**:\n"
         "  1. Register a new user.\n"
         "  2. Login with the newly registered user's credentials.\n"
         "  3. Add a product to the cart.\n"
         "  4. Open a new tab and navigate to the website.\n"
         "  5. Verify if the cart contents are still present."
         ),
        (10, "Verify that the Privacy Policy, About Us are displayed correctly",
         "- **Objective**: To verify that the Privacy Policy and About Us pages are displayed correctly.\n"
         "- **Steps**:\n"
         "  1. Register a new user.\n"
         "  2. Login with the newly registered user's credentials.\n"
         "  3. Click on the Privacy Policy link.\n"
         "  4. Verify if the Privacy Policy page is displayed correctly.\n"
         "  5. Click on the About Us link.\n"
         "  6. Verify if the About Us page is displayed correctly."
         ),
        (11, "Verify that contact us option is working correctly",
         "- **Objective**: To verify that the Contact Us option is working correctly.\n"
         "- **Steps**:\n"
         "  1. Navigate to the Contact Us page.\n"
         "  2. Enter name, email, and message.\n"
         "  3. Click on the Contact Us button.\n"
         "  4. Verify if the Contact Us form submission is successful."
         ),
        (12, "Ensure that the links on the QKART advertisement are clickable",
         "- **Objective**: To ensure that the links on the QKART advertisement are clickable.\n"
         "- **Steps**:\n"
         "  1. Register a new user.\n"
         "  2. Login with the newly registered user's credentials.\n"
         "  3. Add a product to the cart.\n"
         "  4. Go to the checkout page and place the order.\n"
         "  5. Verify if the QKART advertisements are displayed.\n"
         "  6. Verify if the links on the QKART advertisements are clickable."
         ),
    ]

    for testcase_number, testcase_name, description in test_cases:
        readme_content += generate_testcase_entry(testcase_number, testcase_name, description)

    with open("README.md", "w") as readme_file:
        readme_file.write(readme_content)

if __name__ == "__main__":
    generate_readme()

