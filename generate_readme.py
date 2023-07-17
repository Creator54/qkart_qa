#!/usr/bin/env python

import os
import re
import subprocess

def generate_testcase_entry(testcase_number, testcase_name, description):
    entry = f"<details>\n<summary><b>{testcase_name}</b></summary>\n"
    entry += "<ul>\n<details>\n<summary>Details</summary>\n\n"
    entry += f"{description}\n</details>\n"
    entry += "<details>\n<summary>Screenshots</summary>\n\n"

    # Get the list of screenshots for the current test case
    screenshots = [f for f in os.listdir("screenshots") if re.match(f"TestCase{testcase_number:02d}_.*\.png", f)]

    # Sort the screenshots to ensure they are added in the correct order
    screenshots.sort()

    # Add the screenshots to the entry with relative paths
    for screenshot in screenshots:
        entry += f"![{screenshot}](./screenshots/{screenshot})\n\n"

    entry += "</details>\n</ul>\n</details>\n\n"
    return entry


def generate_readme():
    readme_content = """\
## Introduction
The project covers different scenarios to validate the functionality and behavior of the E-Commerce application.

## Test Cases
"""

    # Run the grep command and store the output
    grep_command = "grep -e \"End TestCase.*\" logs.txt | grep -o \"Test Case [0-9]*: .*\""
    output = subprocess.check_output(grep_command, shell=True, text=True)

    # Split the output into lines to create a Python list
    test_cases_list = output.strip().split("\n")

    # Add test case entries
    test_cases = [
        # Test case number, Test case name, Test case description
        (1,test_cases_list[0],
         "- **Objective**: To verify that a new user can successfully register and login with valid credentials.\n"
         "- **Steps**:\n"
         "  1. Navigate to the user Registration page of QKart\n"
         "  2. Enter the username and password\n"
         "  3. Enter the confirm password\n"
         "  4. Click on the Register button\n"
         "  5. Navigate to the Login page\n"
         "  6. Login with the the newly created user credentials\n"
         ),
        (2,test_cases_list[1],
         "- **Objective**: To verify that the registration process prevents using an existing username.\n"
         "- **Steps**:\n"
         "  1. Navigate to the Registration page of QKart\n"
         "  2. Enter the user name of an existing user\n"
         "  3. Enter password and confirm password\n"
         "  4. Click on the Register button\n"
         ),
        (3,test_cases_list[2],
         "- **Objective**: To verify that All results displayed on the page should contain the name 'yonex'', There should be a 'No products found' message\n"
         "- **Steps**:\n"
         "  1. Navigate to home page\n"
         "  2. Search for text : 'yonex' in the search box\n"
         "  3. Ensure that the results shown contain the search text in their name\n"
         "  4. Search for text: 'Gesundheit'\n"
         "  5. Ensure that no products found message is displayed\n"
         ),
        (4,test_cases_list[3],
         "- **Objective**: Size chart Link should be present, On Click  the size chart should be displayed, The contents of the size chart are correct, The Size Selection drop down must be present for items with size chart, Each size present in the size selection dropdown must have corresponding entry on the size chart\n"
         "- **Steps**:\n"
         "  1. Navigate to home page\n"
         "  2. Search for text: 'UNIFACTOR Mens Running Shoes'\n"
         "  3. Verify that the Size Chart Link Exists\n"
         "  4. Click on the size chart link and check the contents of the size chart\n"
         "  5. Verify the existence of size selection drop down\n"
         "  6. Ensure each size present in the size selection dropdown has corresponding reference on the size chart\n"
         ),
        (5,test_cases_list[4],
         "- **Objective**: To verify the The user is re-directed to the checkout page, The contents of the cart on the checkout page should be correct, The user should be redirect to the order success page, Order successful message should be displayed\n"
         "- **Steps**:\n"
         "  1. Register a new user\n"
         "  2. Login using this new user\n"
         "  3. Add the following products in to the cart 'YONEX Smash Badminton Racquet x 1', 'Tan Leatherette Weekender Duffle x1'\n"
         "  4. Click on Checkout\n"
         "  5. Add a new address\n"
         "  6. Select the added address using radio button\n"
         "  7. Click on Place order\n"
         "  8 . Ensure that the order is placed\n"
         ),
        (6,test_cases_list[5],
         "- **Objective**: To verify that the The Cart on the checkout page should contain the following items 'The Minimalist Slim Leather Watch x1'\n"
         "- **Steps**:\n"
         "  1. Navigate to the home page\n"
         "  2. Add the follwing products to the cart 'The Minimalist Slim Leather Watch x2', 'Bonsai Spirit Tree Table Lamp x1'\n"
         "  3. Remove 1 Qty of The Minimalist Slim Leather Watch from the cart\n"
         "  4. Remove 1 Qty of Bonsai Spirit Tree Table Lamp from the cart\n"
         "  5. Click on Checkout\n"
         "  6. Verify the contents of cart on the checkout page\n"
         "  7. Log out\n"
         ),
        (7,test_cases_list[6],
         "- **Objective**: To verify that the The Checkout button must be present as there are items already added to the cart, The contents of the cart must be 'Stylecon 9 Seater RHS Sofa Set x1', 'The Minimalist Slim Leather Watch x3'\n"
         "- **Steps**:\n"
         "1. Register a new user\n"
         "2. Login using the registered user\n"
         "3. Add the following items in the cart: 'Stylecon 9 Seater RHS Sofa Set x1', 'The Minimalist Slim Leather Watch x3'\n"
         "4. Logout\n"
         "5. Login using the same user\n"
         "6. Verify the existence of checkout button\n"
         "7. Verify the existence of the items added to cart\n"
         ),
        (8, test_cases_list[7],
         "- **Objective**: To verify that an insufficient balance error is thrown when the wallet balance is not enough to place an order.\n"
         "- **Steps**:\n"
         "1. Register a new user\n"
         "2. Login using the registered user\n"
         "3. Add the following items in the cart 'Stylecon 9 Seater RHS Sofa Set x10'\n"
         "4. Check out\n"
         "5. Add a new address\n"
         "6. Select the added address using radio button\n"
         "7. Click on Place order\n"
         "8. Verify if the order is placed\n"
         ),
        (9, test_cases_list[8],
         "- **Objective**: To verify that a product added to the cart is available when a new tab is opened.\n"
         "- **Steps**:\n"
         "1. Register a new user\n"
         "2. Login with the newly created user\n"
         "3. Search for th e product 'YONEX Smash Badminton Racquet''\n"
         "4. Add the above mentioned product to the cart\n"
         "5. Open a new tab and go to the QKART home page\n"
         "6. Check if the product added in step 4 is present in the cart\n"
         ),
        (10, test_cases_list[9],
         "- **Objective**: To verify that the Privacy Policy and About Us pages are displayed correctly.\n"
         "- **Steps**:\n"
         "1. Navigate to QKART page\n"
         "2. Click on the Privacy Policy Link\n"
         "3. Click on the about us Link\n"
         ),
        (11, test_cases_list[10],
         "- **Objective**: To verify that the Contact Us option is working correctly.\n"
         "- **Steps**:\n"
         "1. Navigate to QKART page\n"
         "2. Click on the contact us link\n"
         "3. Update the contact us details\n"
         "4. Click on close\n"
         ),
        (12, test_cases_list[11],
         "- **Objective**: To ensure that the links on the QKART advertisement are clickable.\n"
         "- **Steps**:\n"
         "1. Navigate to QKART page\n"
         "2. Register a new user\n"
         "3. Login using the registered user\n"
         "4. Search for product: 'YONEX Smash Badminton Racquet'\n"
         "5. Buy the product\n"
         "6. In the last page, check if the advertisement links are clickable\n"
         ),
    ]

    for testcase_number, testcase_name, description in test_cases:
        readme_content += generate_testcase_entry(testcase_number, testcase_name, description)


    # conclude with total passed TestCases.
    readme_content +="""\
## Results

"""
    grep_command = "grep -e \"test cases Passed\" logs.txt"
    output = subprocess.check_output(grep_command, shell=True, text=True)

    readme_content+=output

    with open("README.md", "w") as readme_file:
        readme_file.write(readme_content)

if __name__ == "__main__":
    generate_readme()

