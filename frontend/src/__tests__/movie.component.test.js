import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
import Movie from '../components/movie.component';
import MovieService from '../services/movie.service';

jest.mock('../services/movie.service', () => ({
  getMovies: jest.fn(),
  createMovie: jest.fn(),
  updateMovie: jest.fn(),
  deleteMovie: jest.fn(),
}));

describe('Movie Component', () => {

  beforeEach(() => {
    window.alert = jest.fn();
  });

  test('should render the list of movies', async () => {
    MovieService.getMovies.mockResolvedValue([
      { id: 1, name: 'Inception', timeDuration: 'PT2H28M' },
      { id: 2, name: 'Interstellar', timeDuration: 'PT2H49M' }
    ]);

    await act(async () => {
      render(<Movie />);
    });
    
    await waitFor(() => screen.getByText('Inception'));
    await waitFor(() => screen.getByText('Interstellar'));

    expect(screen.getByText('Inception')).toBeInTheDocument();
    expect(screen.getByText('Interstellar')).toBeInTheDocument();
  });

  test('should create a movie', async () => {
    MovieService.getMovies.mockResolvedValue([
      { id: 3, name: 'Dunkirk', timeDuration: 'PT1H46M' },
    ]);

    await act(async () => {
      render(<Movie />);
    });

    fireEvent.change(screen.getByLabelText('Name:'), { target: { value: 'Dunkirk' } });
    fireEvent.change(screen.getByLabelText('Duration (in minutes):'), { target: { value: '106' } });
    await act(async () => {
      fireEvent.click(screen.getByRole('button', { name: 'Create Movie' }));
    });
    await waitFor(() => screen.getByText('Dunkirk'));

    expect(screen.getByText('Dunkirk')).toBeInTheDocument();
  });

  test('should update a movie', async () => {
    MovieService.getMovies.mockResolvedValue([
      { id: 1, name: 'Inception Updated', timeDuration: 'PT2H28M' },
    ]);
    await act(async () => {
      render(<Movie />);
    });
    
    fireEvent.click(screen.getAllByText('Update')[0]);

    fireEvent.change(screen.getByLabelText('Name:'), { target: { value: 'Inception Updated' } });
    fireEvent.change(screen.getByLabelText('Duration (in minutes):'), { target: { value: '150' } });
    
    fireEvent.click(screen.getByText('Save'));

    await waitFor(() => screen.getByText('Inception Updated'));
    
    expect(screen.getByText('Inception Updated')).toBeInTheDocument();
  });

  test('should delete a movie', async () => {
    MovieService.getMovies.mockResolvedValue([
      { id: 1, name: 'Inception', timeDuration: 'PT2H28M' },
      { id: 2, name: 'Interstellar', timeDuration: 'PT2H49M' }
    ]);

    await act(async () => {
      render(<Movie />);
    });
    fireEvent.click(screen.getAllByText('Delete')[0]);

    MovieService.getMovies.mockResolvedValue([
      { id: 2, name: 'Interstellar', timeDuration: 'PT2H49M' }
    ]);

    await waitFor(() => expect(screen.queryByText('Inception')).toBeNull());
  });

});
