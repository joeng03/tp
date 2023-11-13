---
layout: page
title: User Guide
---
## Welcome to PharmHub!

--------------------------------------------------------------------------------------------------------------------

**PharmHub – Where Precision Meets Prescription**
<br><br>
Are you a small remote pharmacist seeking to elevate precision and efficiency in your workplace?
<br><br>
Look no further, as PharmHub is here to answer your prayers.
PharmHub is a powerful desktop application designed for the meticulous management of patients and their medication orders,
while also always working behind the scenes ensuring every step taken does not have any errors.
It's not just another GUI app; it's your optimized solution, with a focus on efficiency through a Command Line Interface (CLI) 
complemented by the familiar benefits of a Graphical User Interface (GUI).
Why PharmHub? If you're adept at typing, PharmHub empowers you to breeze through patient and order management tasks faster than ever before,
outpacing traditional GUI applications.

Getting started is a breeze:

* First, familiarize yourself with key [Definitions](#definition).
* Next, familiarise with our GUI with our [Application Navigation](#application-navigation).
* Then, dive into our hands-on [Quick Start](#quick-start) guide.
* Explore the array of capabilities in our [Features](#features) section, where each command's functionality is unveiled.
* For seasoned users, our [Command Summary](#command-summary) serves as your go-to resource, encompassing everything you need to master the application.
* Refer to the [Table Of Content](#table-of-contents) for quick navigation and [Trouble Shooting](#trouble-shooting) for any issues faced.

PharmHub isn't just software; it's your ally in precision pharmacy management. Let's embark on this journey together!

--------------------------------------------------------------------------------------------------------------------
## Table of Contents
1. [Definition](#definition)
   1. [Person](#person)
   2. [Order](#order)
   3. [Status](#status)
   4. [Medicine](#medicine)
   5. [Index](#index)
   6. [Fields](#fields)
2. [Application Navigation](#application-navigation)
3. [Quick Start](#quick-start)
4. [Features](#features)
   1. [Help](#viewing-help--help)
   2. [Person Related Commands](#person-related-commands)
      1. [Add Person](#adding-a-person-addp)
      2. [list Person](#listing-all-persons--listp)
      3. [View Person](#viewing-a-person-viewp)
      4. [Edit Person](#editing-a-person--editp)
      5. [Delete Person](#deleting-a-person--deletep)
   3. [Medicine Related Commands](#medicine-related-commands)
       1. [Add Medicine](#adding-a-new-medicine--addm)
       2. [list Medicine](#listing-all-medicines--listm)
       3. [Find Medicine](#locating-a-medicine-by-name--findm-)
       4. [Delete Medicine](#deleting-a-medicine--deletem)
       5. [Add Short Form Medicine](#adding-and-deleting-short-form--sfm)
   4. [Order Related Commands](#person-related-commands)
       1. [list Order](#listing-all-orders--listo-)
       2. [View Order](#viewing-an-order--viewo-)
       3. [Add Order](#adding-a-new-order--addo-)
       4. [Update Order Status](#updating-the-status-of-an-order--updates-)
       5. [Find Order](#filteringfinding-order-by-status-and-medicines-findo)
       6. [Delete Order](#deleting-an-order--deleteo-)
   5. [Undo](#undoing-an-action--undo-)
   6. [Redo](#redoing-an-action-after-an-undo--redo-)
   7. [Clear](#clearing-all-entries--clear)
   8. [Exit](#exiting-the-program--exit)
5. [Command Summary](#command-summary)
6. [Trouble Shooting](#trouble-shooting)
   1. [FAQ](#faq)
   2. [Common Errors](#common-errors)
7. [Glossary](#glossary)

--------------------------------------------------------------------------------------------------------------------

## Definition
This section provides an introduction to the terminology used in this user guide. 

### Person

For the entire user guide the term person is used to refer to the patient of which the pharmacist is addressing.
Persons are the people with whom you track your Orders and information for.   
To create an order with a person, they must first have been added to the app.

A person has the following attributes:
- Name
- Phone number
- Email Address
- Address
- Allergy(If the person has one. Can be one or more)
- Tag(Optional for additional remarks/inputs for the person)

A person is uniquely identified by their name. No two persons can have the same name and names are case-insensitive.

### Order

An order refers to a purchase of one or more medicines by a particular person in the person list.

An Order has the following attributes:
- Order Number
- Person (Who the order belongs to)
- Medicine (What is in the order. Can be one or more)
- Status of the order

Orders are uniquely identified by their order numbers. No two orders can have the same order number.   
No order containing a medicine that the person is allergic to can be added without a special flag.  
Orders will automatically tag under the <span style="color: red;">PENDING</span>
Status when added.

### Status

Status is used to describe the process at which the order is at.
Status can only be updated/modified following their chronological order.

<span style="background-color: red; border-radius: 20%; padding: 1px; color: white">PENDING/PD</span>
->
<span style="background-color: blue; border-radius: 20%; padding: 1px; color: white">PREPARING/PR</span>
->
<span style="background-color: green; border-radius: 20%; padding: 1px; color: white">COMPLETED/CP</span>
->
<span style="background-color: black; border-radius: 20%; padding: 1px; color: white">CANCELED/CC</span>

Status can be identified either in their full form or in their short form as shown above (FullForm/ShortForm). Status are all case-insensitive as well.

### Medicine

Medicine are objects that can be added to an order when it is being placed for a person.

A Medicine has the following attribute:
- Full Name of the medication
- An optional short form for the medication

Medicines are uniquely identified by their full and short form names. No two medicine can have the same names.

### Index

Many commands rely on index for execution. Index refers to the numbering in the last shown person/order/medicine list.
All indices have to be positive integers.

As you can refer to the image below index 1 refer to OrderNumber #1 while index 3 refer to OrderNumber #5.

![listp](images/listo.png)

Note: The Index will be based on the command being called and not the displayed list, 
if a command of viewp(view person) is being called, 
the index taken will be based on the person list and not this list being shown currently.

### Fields
* Fields are the different parts of a command.  
e.g. in `editp 1 n/Alex no/penicillin`, there are four fields. Namely, `editp`, `1`, `Alex`, `penicilllin`.    
* Spaces before and after fields will be ignored.
If there are multiple spaces between two words in fields, only one space would be retained.  
As such `Alex Yeoh` will produce the same result as `Alex     Yeoh`  
* To input a `/` character in a field, use another `/` before it.   
If there is an odd  number of consecutive `/`, one of them will be ignored.  
  e.g To input `Roy s/o Balakrishnan`, use `Roy s//o Balakrishnan`

The following fields are used for commands:

| Field         | Prefix | Format                                                                                                        | Example                                    |
|---------------|--------|---------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| Index         | -      | Must be a positive integer                                                                                    | `1`, `2`, `3`                              |
| Keyword       | -      | Must not be empty                                                                                             | `pan`, `Ah Tan s//o Ah Tan Tan`            |
| Name          | n/     | Must only contain alphanumeric, space, dot, slash, <br/> hyphen and single quote characters. Case insensitive | `Roy s//o Balakrishnan`, `Connell O'Brien` |
| Phone number  | p/     | Must be numeric and be at least 3 digits long                                                                 | `999`, `68741616`                          |
| Email address | e/     | Must be a valid email address                                                                                 | `roy@gmail.com`, `Alex123@outlook.com`     |
| Tags          | t/     | Optional for use                                                                                              | `Diabetic`, `Age 72`                       |
| Address       | a/     | Must not be empty                                                                                             | `Sentosa cove`, `Pulau NTU`                |
| Status        | s/     | Must be a valid Status (Refer to [Status](#status))                                                           | `PENDIND`, `Cc`                            |
| Medicine      | m/     | Must not be empty and is not case sensitive.                                                                  | `metformin`, `panadol`                     |
| Allergy       | no/    | Must be a medicine name in PharmHub or its shortform.                                                         | `panadol`, `pan`                           |
| Commands      | -      | Must be the first part of input and is case-insensitive                                                       | `addo`, `listm`                            |


---

## Application Navigation

Below shows a guide on how you can navigate around our interactive Graphical User Interface (GUI)

![Navigation](images/navigation.png)

| Component                  | Description                                                 | Remarks                                                          |
|----------------------------|-------------------------------------------------------------|------------------------------------------------------------------|
| Help Button                | Shows URL to our User Guide.                                | -                                                                |
| Command Input Field        | Type commands here and press `Enter` to execute them.       | -                                                                |
| Result Display Box         | Shows the result of the command execution.                  | -                                                                |
| Person Information Display | Shows Detailed information on a person.                     | Displays Name, Email, allergy and Orders under this person.      |
| PersonList                 | Shows a list of person.                                     | List can be filtered by Keywords in the names.                   |
| Person Card                | Shows a particular person with minimal details in the list. | Shows the Name, Allergies, Number, Address and Email.            |
| Order Information Display  | Shows Detailed information on an order.                     | Displays status, Order Number, Person of the order and Medicine. |
| Order List                 | Shows a list of orders.                                     | List can be filtered by Status or Medicine in the order or Both. |
| Order Card                 | Shows a particular order with minimal details in the list.  | Shows Order Number, Name, Address and Medicine.                  |
| Medicine List              | Shows a list of medicine in the system.                     | List can be filtered by Keywords in the medicines.               |
| Medicine Card              | Shows a particular medicine with all the details.           | Shows the Full and Short form of the medicine.                   |

---

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.
   * Run `java --version` in your command terminal to see the java version.
   * For both `Mac` and `Window` user click [here](https://nus-cs2103-ay2324s1.github.io/website/admin/programmingLanguages.html) for a guide to download the required `java` language for your computer.

2. Download the latest `PharmHub.jar` from [here](https://github.com/AY2324S1-CS2103T-W08-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your PharmHub.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar pharmhub.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `listp` : Lists all people.
   
   * `listo` : Lists all order.
   
   * `listm` : Lists all medicine.

   * `addm m/ibuprofen`: Adds a medicine named `Ibuprofen` to PharmHub.

   * `addp n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a person named `John Doe` to PharmHub.

   * `addo 1 m/ibuprofen o/123`: Adds an order with order number `123` for person indexed `1` to PharmHub.

   * `viewp 1` : views in detail index 1 of the person list.(The Displayed list does not have to be person to view person)

   * `viewo 1` : views in detail index 1 of the order list.
   
   * `deletep 3` : Deletes the 3rd patient shown in the current list.

   * `clear` : Deletes all people.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addp n/NAME`, `NAME` is a parameter which can be used as `addp n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Person Related Commands

### Adding a person: `addp`

Adds a person to PharmHub.

Format: `addp n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG] [no/ALLERGY]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags or allergies (including 0)
</div>

Examples:
* `addp n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 no/paracetamol no/penicillin`
* `addp n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `listp`

Shows an interactive list of all persons in PharmHub.

Format: `listp`

![listp](images/listp.png)

### Viewing a person `viewp`

Displays the specified person with more details in the info panel.

Format: `viewp INDEX`

Examples:
* `listp` followed by `viewp 2` opens the 2nd person in the list into the info panel.

### Editing a person : `editp`

Edits an existing person in the PharmHub.

Format: `editp INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [no/ALLERGY]…​ [ia/]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the last displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* This also edits orders belonging to the person.
* At least one of the optional fields must be provided.
* This command will not be able to add/delete orders to this person
* Existing values will be updated to the input values.
* When editing tags or allergies, the existing tags or allergies of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.
* You can remove all the person’s allergies by typing `no/` without
    specifying any allergies after it.
* If the editing causes the person to be allergic any of the orders belonging to them, warning will be raised.
* The warning can be overridden by adding the `ia/` to the command

Examples:
*  `editp 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `editp 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `editp 3 no/` Edits the allergies of the 3rd person to be empty.

### Locating persons by name: `findp`

Finds persons whose names contain any of the given keywords.

Format: `findp [n/KEYWORD [MORE_KEYWORDS]…] [p/PHONE_NUMBER] [e/EMAIL] [t/KEYWORD [MORE_KEYWORDS]…] [no/KEYWORD [MORE_KEYWORDS]…]`

* The search for name, tags and allergies are case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* The search for phone number and email are case-sensitive and must be an exact match.
  e.g. `9123456` will not match `91234567` and `johndoe@example` does not match `johndoe@example.com`


Examples:
* `findp n/Alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)
* `findp no/paracetamol penicillin`

### Deleting a person : `deletep`

Deletes the specified person from PharmHub.

Format: `deletep INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the last displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Deleting a person also deletes all orders corresponding to the person from PharmHub.
</div>
Examples:
* `listp` followed by `deletep 2` deletes the 2nd person in PharmHub.
* `findp n/Betsy` followed by `deletep 1` deletes the 1st person in the results of the `find` command.

---

### Medicine Related Commands

### Adding a new medicine : `addm`

Adds a new medication into PharmHub.  

Format: `addm m/MEDICINE_NAME`

* The given name shouldn't match the name or short form of any medicine in PharmHub.   
* Medicine names are case-insensitive.

Example:
* `addm m/Aspirin`

### Listing all medicines : `listm`

Shows a list of all medicines in PharmHub.  

Format: `listm`

![listm](images/listm.png)

### Locating a medicine by name : `findm` 

Finds all medicines whose name or short form contains any of the given keywords.  

Format : `Format: findm KEYWORD [MORE_KEYWORD]…`

* The search is case-insensitive. e.g `pan` will match `Panadol`
* The medicine name and it's short form is searched.
* Medicines matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `ol en` will return `Panadol`, `Ibuprofen`


<div markdown="span" class="alert alert-info">:information_source: **Note:** Unlike `findp`, partial words will be matched e.g. `para` will  match `Paracetamol`.


Examples:
* `findm ol`
* `findm ol para`

### Deleting a medicine : `deletem`

Deletes the specified medicine from PharmHub.

Format: `deletem INDEX`

* Deletes the medicine at the specified `INDEX`.
* The index refers to the index number shown in the last displayed medicine list.
* The index **must be a positive integer** 1, 2, 3, …​ 
* The command will be blocked if there are existing orders with the specified medicine 
  or persons allergic to the specified medicine.  


Example:
* `deletem 2`

### Adding and Deleting short form : `sfm`

Adds or Deletes a short form from the specified medicine from PharmHub.

Format: `sfm INDEX [m/SHORT_FORM] [d/]`

* Index refers to index of medicine in the last displayed medicine list.
* At least one of `m/` or `d/` should be provided.
* If the `d/` is not provided, the given short form will be added to the medicine at the specified `INDEX`.  
  * The provided short form must not be empty and must not be same as any existing medicine name or short form in PharmHub.  
  * Any existing short form of the medicine will be overwritten.
  * After this, the short form can be used interchangeably with the medicine name.  
* If the `d/` is provided, the short form(if any) of the medicine at the specified `INDEX` will be deleted.  
  * Any short form provided using `m/` will be ignored.

Example:
* `sfm 1 m/pan`

### Adding a new order : `addo`

Adds a new order of the given medication(s) corresponding to a person into the system.

Format: `addo INDEX o/ORDER_NUMBER m/MEDICINE_NAME [m/MEDICINE_NAME]…​ [ia/]`

* Orders are created automatically having a `status` of `pending`.
* Orders can only be created for a person in the index range, and for a known `medicine`
* Orders created for persons with an allergy to any of the medications in the order will raise a warning.
* The warning can be overridden by adding the `ia/` to the command

Parameters:
* `INDEX` - index of patient who is ordering the medicine as shown in the last displayed patient list.
* `ORDER_NUMBER` - the order number of this order specified by the invoice.
* `MEDICINE_NAME` - the name of medicine being ordered.

Examples:
* `addo 1 o/618457 m/panadol`
* `addo 3 o/438756 m/paracetamol`


### Updating the status of an order : `updates`

Updates the status of the order to the designated status.

Format: `updates INDEX s/STATUS`

* Statuses: `Pending (pd) -> Preparing (pr) -> Completed (cp) -> Cancelled (cc)`, in increasing hierarchy
* Statuses can only be updated upwards. Once the status of an order progresses to the next stage, it cannot go back.
* Statuses can be updated by skipping the hierarchy. `Pending -> Cancelled`
* Shorthands can be used in replacement of the full names of the statuses

Example: 
* `updates 1 s/completed`
* `updates 1 s/COMPLETED`
* `updates 1 s/cp`
* `updates 1 s/CP`


### Listing all orders : `listo` 

Shows an interactive list of all orders in PharmHub.

Format: `listo`

![listo](images/listo.png)

### Viewing an order : `viewo` 

Shows the order in the info panel.

Format: `viewo`

### Filtering/Finding Order by status and medicines: `findo`

Finds orders whose status and medicine satisfies both inputs.

Format: `findo s/STATUS [m/KEYWORD [MORE_KEYWORDS]…]`

* The search is case-insensitive. e.g `PANADOL` will match `Panadol`, `COMPLETED` or `CP` will match `Completed`.
* User input can find orders base on either status or medicines or both(but both will have to be satisfied).
* Status can only be `Pending/PD Preparing/PR Completed/CP Cancelled/CC`, any other inputs will be invalid.
* Medicine can be written both in their short form and full form. `pan` will match `Panadol`.
* Multiple Medicine can be used as input separated by a blank space but only one status can be used.
* Orders that contain any one of the medication/Status will be shown.

Examples:
* `findo m/Panadol Ibuprofen` returns any orders with either `Panadol` or `Ibuprofen`.
* `findo s/pd m/Panadol` returns any orders that is both `Pending` and contains `Panadol`.
  ![result for 'findo s/pd m/Panadol'](images/findOrder2Input.png)

### Deleting an order : `deleteo` 

Deletes the specified order from PharmHub.

Format: `deleteo INDEX`

Example: `deleteo 2`


### Undoing an action : `undo` 

Undoes the last data-modifying action.

Format: `undo`

* Limited to last 30 actions.
* Does not undo Ui views (eg. find, view commands). 

Example: 
* `addp` -> `listp` -> `undo` will undo the `addp` command

### Redoing an action (after an undo) : `redo` 

Negates the effect of the last undo.

Format: `redo`

* The undo command must have been the latest data-modifying command.
* Once a non-`undo` data-modifying command is executed, redoing undoes before that non-`undo` command is no longer possible

Example:
* `addp` -> `undo` -> `addp` -> `redo`  will throw an error
* `addp` -> `undo` -> `listp` -> `redo` will redo the `addp` command successfully


### Clearing all entries : `clear`

Clears all entries from PharmHub.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

PharmHub data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

PharmHub data are saved automatically as a JSON file `[JAR file location]/data/pharmhub.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, PharmHub will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>


--------------------------------------------------------------------------------------------------------------------





## Command Summary

| Action                    | Format, Examples                                                                                                                                                                                |
|---------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **List People**           | `listp`                                                                                                                                                                                         |
| **Find Person**           | `findp [n/KEYWORD [MORE_KEYWORDS]…] [p/PHONE_NUMBER] [e/EMAIL] [t/KEYWORD [MORE_KEYWORDS]…] [no/KEYWORD [MORE_KEYWORDS]…]`<br> e.g., `findp n/James Jake`                                       |
| **View Person**           | `viewp INDEX` <br> e.g., `viewp 1`                                                                                                                                                              |
| **Add Person**            | `addp n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG] [no/ALLERGY]…​` <br> e.g., `addp n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague no/aspirin` |
| **Edit Person**           | `editp INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG] [no/allergy]…​`<br> e.g.,`editp 2 n/James Lee e/jameslee@example.com`                                                      |
| **Delete Person**         | `deletep INDEX`<br> e.g., `deletep 3`                                                                                                                                                           |
| **List Orders**           | `listo`                                                                                                                                                                                         |
| **Find Order**            | `findo [s/STATUS] [m/KEYWORD [MORE_KEYWORDS]…]`<br> e.g., `findo s/cp m/pen`                                                                                                                    |
| **View Order**            | `viewo ORDER_NUMBER` <br> e.g., `viewo 12345`                                                                                                                                                   |
| **Add Order**             | `addo INDEX o/ORDER_NUMBER m/MEDICINE_NAME [m/MEDICINE_NAME]…` <br> e.g., `addorder 3 o/438756 m/claritin`                                                                                      |
| **Update Order Status**   | `updates INDEX s/STATUS`<br> e.g., `updates s/cancelled`                                                                                                                                        |
| **Delete Order**          | `deleteo INDEX`<br> e.g., `deleteo 3`                                                                                                                                                           |
| **List Medicine**         | `listm`                                                                                                                                                                                         |
| **Find Medicine**         | `findm KEYWORD [MORE_KEYWORDS]…`  <br/> e.g., `findm ol`                                                                                                                                        |
| **Add Medicine**          | `addm m/MEDICINE_NAME`<br/> e.g., `addm m/panadol`                                                                                                                                              |
| **Delete Medicine**       | `deletem INDEX` <br/> e.g., `deletem 1`                                                                                                                                                         |
| **Add/Delete Short Form** | `sfm INDEX [m/SHORT_FORM] [d/]` <br/> e.g., `sfm 1 m/met`                                                                                                                                       |
| **Undo**                  | `undo`                                                                                                                                                                                          |
| **Redo**                  | `redo`                                                                                                                                                                                          |
| **Clear**                 | `clear`                                                                                                                                                                                         |
| **Help**                  | `help`                                                                                                                                                                                          |
| **Exit**                  | `exit`                                                                                                                                                                                          |

---

## Trouble Shooting

### FAQ

**Q**: The application is not running on my computer.<br>
**A**: Refer to our [Quick Start](#quick-start) to ensure that you downloaded the correct java version.

**Q**: Will I lose my data if i exit the application?<br>
**A**: No you will not. The data is stored on your computer and as long as you don't delete the `.jar` file, your data will be stored.

**Q**: I accidentally deleted the `PharmHub.jar` file can i get my data back?<br>
**A**: Unfortunately, the data cannot be recovered once the `.jar` file is deleted so make sure to keep it at a secure location.

**Q**: Is my patient data secure?<br>
**A**: Yes, as long as your computer is secure no one else will be able to access the information.

**Q**: Is internet connection required?<br>
**A**: Nope, PharmHub do not require internet connection.

Click [here](https://github.com/AY2324S1-CS2103T-W08-4/tp/issues) to view some issues posted by some of our users to see if it solves yours.
<br>
Click [here](https://github.com/AY2324S1-CS2103T-W08-4/tp/issues/new) to feedback on issues you faced that we didn't answer on.


### Common Errors

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. Common Error messages

   1. `Unknown Command` Error
      <br>![UnknownCommand](images/errorMessage/UnknownCommand.png)
      <br> Check [Command Summary](#command-summary) and make sure the input command is a valid commmand.
      <br><br>
   2. `Ivalid Command Format` Error
      <br>![InvalidCommandFormat](images/errorMessage/InvalidCommandFormat.png)
      <br> Either read the error message and edit accordingly or look at the command [Features](#features) for detailed explanation.
      <br><br>
   3. `Missing Field` Error
      <br>![MissingField](images/errorMessage/MssingField.png)
      <br> Check the command [feature](#features) to identify the missing input.
      <br><br>
   4. `Invalid Index` Error
      <br>![InvalidIndex](images/errorMessage/InvalidIndex.png)
      <br> Check through the display list and ensure a valid index is chosen. [Index](#index)
      <br><br>
   5. `Invalid Input` Error
      <br>![InvalidInput](images/errorMessage/InvalidInput.png)
      <br> Refer to the [fields](#fields) to ensure the validity of the input fields.
      <br><br>
   6. `Invalid Chronological Order` Error
      <br> Ensure that the [status](#status) is of correct chronological order.
      <br>![InvalidChronological](images/errorMessage/InvalidChronological.png)
   
--------------------------------------------------------------------------------------------------------------------

## Glossary

| Term                 | Meaning                                                                                                               |
|----------------------|-----------------------------------------------------------------------------------------------------------------------|
| **CLI**              | Command Line Interface(CLI) is a text based interface where users can input commands.                                 |
| **Command Terminal** | A program which allows users to enter commands that the computer processes.                                           |
| **`cd`**             | The command used in command terminal to change directory.                                                             |
| **Gui**              | Graphical User Interface(GUI) is the digital interface that the user interacts with.                                  |
| **Java**             | Java is a widely used programming language and is used in PharmHub.                                                   |
| **Jar**              | Java Archive contains all of the various components that make up a Java application, in this case PharmHub.           |
| **Json**             | JavaScript Object Notation(Json) is a text format for storing data. It is used by PharmHub to store application data. |  
