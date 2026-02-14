import React, { useState } from 'react';
import { GENRES } from '../../utils/constants';
import '../../styles/SearchBar.css';

const SearchBar = ({ onSearch, onGenreChange }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedGenre, setSelectedGenre] = useState('');

  const handleSearch = (e) => {
    e.preventDefault();
    onSearch(searchTerm);
  };

  const handleGenreChange = (e) => {
    const genre = e.target.value;
    setSelectedGenre(genre);
    onGenreChange(genre);
  };

  return (
    <div className="search-bar">
      <form onSubmit={handleSearch} className="search-form">
        <input
          type="text"
          placeholder="Search movies..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="search-input"
        />
        <button type="submit" className="search-button">
          Search
        </button>
      </form>

      <select
        value={selectedGenre}
        onChange={handleGenreChange}
        className="genre-select"
      >
        <option value="">All Genres</option>
        {GENRES.map((genre) => (
          <option key={genre} value={genre}>
            {genre}
          </option>
        ))}
      </select>
    </div>
  );
};

export default SearchBar;
