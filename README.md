<h1 align="center">Coffee Utilities Package ☕</h1>

> Documentation: [📖 JavaDoc](https://rafandoo.dev/cup/)

## 📝 Project description

<p align="justify">
Coffee Utilities Package (CUP) is a Java library that provides a set of utilities for Java developers. The main 
objective of this project is to facilitate the development of Java applications, providing a set of tools that can be 
used in various contexts. The project is divided into modules, each of which provides a set of utilities for a specific 
context. The modules are independent of each other, so you can use only the modules you need in your project. The 
project is open source and is available on GitHub. You can contribute to the project by submitting bug reports, feature 
requests, or code contributions. The project is licensed under the Apache License 2.0.
</p>

## 🤔 Problem definition

<p align="justify">
Java is a powerful programming language that is widely used in the software development industry. However, Java does 
not provide a complete set of utilities for developers. Developers often need to write their own utilities to perform 
common tasks such as reading and writing files, working with dates and times, and working with collections. This can be 
time-consuming and error-prone. The Coffee Utilities Package (CUP) project aims to provide a set of utilities that can 
be used by Java developers to perform common tasks more easily and efficiently.
</p>

## 🛠️ Technologies used

<p align="center">
    <img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle"/>
    <img src="https://img.shields.io/badge/Java-ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
</p>

## 📦 Installation & Usage

### 🧩 Available Modules

This project is modular. You can include the complete package or just specific modules, depending on your needs:

| Module    | Artifact ID | Description                            |
|-----------|-------------|----------------------------------------|
| core      | cup-core    | General-purpose utilities              |
| http      | cup-http    | HTTP request helpers and URL builder   |
| objects   | cup-objects | Object serialization, file exporters   |
| (planned) | cup-files   | File utilities and resource management |

### ☁️ How to Add to Your Project

You can add CUP to your project using Gradle or Maven.
The artifacts are published under the group ID `dev.rafandoo`, and each module can be added individually.

#### 🐘 Using Gradle

Add the desired module dependency to your `build.gradle`:

```groovy
dependencies {
  implementation 'dev.rafandoo:cup-core:{LAST_VERSION}'
}
```

To include other modules, use the corresponding artifact ID:

```groovy
dependencies {
  implementation "dev.rafandoo:cup-http:{LATEST_VERSION}"
  implementation "dev.rafandoo:cup-objects:{LATEST_VERSION}"
}
```

#### 🪶 Using Maven

Add the dependency to your `pom.xml`:

```xml

<dependencies>
  <dependency>
    <groupId>dev.rafandoo</groupId>
    <artifactId>cup-core</artifactId>
    <version>{LAST_VERSION}</version>
  </dependency>
</dependencies>
```

To include other modules, use the corresponding artifact ID:

```xml

<dependencies>
  <dependency>
    <groupId>dev.rafandoo</groupId>
    <artifactId>cup-http</artifactId>
    <version>{LATEST_VERSION}</version>
  </dependency>
  <dependency>
    <groupId>dev.rafandoo</groupId>
    <artifactId>cup-objects</artifactId>
    <version>{LATEST_VERSION}</version>
  </dependency>
</dependencies>
```

### 🧪 Local Development

Clone the repository and build locally:

```bash
git clone https://github.com/rafandoo/cup.git
cd cup

./gradlew build
```

## 🔧 Functionalities

✔️ General utilities.

✔️ HTTP request helpers and URL builder.

✔️ Object inspection and reflection helpers.

✔️ Dynamic class and interface discovery.

✔️ Object export support (JSON, XML, CSV).

✔️ File and path handling utilities.

✔️ String validation helpers.

## 🚀 Future enhancements

✔️ File utilities.

✔️ Cryptography utilities.

✔️ Resource management.

✔️ And more...

## 🔑 License

The [Apache License 2.0](https://github.com/rafandoo/cup/blob/87b6388949953738ce3b39148d2628923fe3c139/LICENSE)

Copyright :copyright: 2024-present - Rafael Camargo
