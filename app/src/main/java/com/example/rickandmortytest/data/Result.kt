package com.example.rickandmortytest.data

data class Result(var info: Info, var results: List<Character>)

data class Info(var count: Int, var pages: Int, var next: String?, var prev: String?)