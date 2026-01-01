
// import { create } from 'zustand';
// import { Guardian } from '../types';
// import { secureStorage } from '../utils/storage';
// import { STORAGE_KEYS } from '../utils/constants';

// interface AuthState {
//   guardian: Guardian | null;
//   isAuthenticated: boolean;
//   isLoading: boolean;
  
//   // Actions
//   setGuardian: (guardian: Guardian) => Promise<void>;
//   clearGuardian: () => Promise<void>;
//   loadGuardian: () => Promise<void>;
//   setLoading: (loading: boolean) => void;
// }

// export const useAuthStore = create<AuthState>((set) => ({
//   guardian: null,
//   isAuthenticated: false,
//   isLoading: true,

//   setGuardian: async (guardian: Guardian) => {
//     try {
//       // Validate and coerce values to strings (SecureStore requires strings)
//       if (!guardian || guardian.id == null || guardian.token == null) {
//         const err = new Error('Invalid guardian data: id and token are required');
//         console.error(err);
//         throw err;
//       }

//       const idStr = String(guardian.id);
//       const tokenStr = String(guardian.token);

//       // Save to secure storage
//       await secureStorage.setItem(STORAGE_KEYS.GUARDIAN_TOKEN, tokenStr);
//       await secureStorage.setItem(STORAGE_KEYS.GUARDIAN_ID, idStr);
      
//       // Update state
//       set({ guardian: { ...guardian, id: idStr, token: tokenStr }, isAuthenticated: true });
//     } catch (error) {
//       console.error('Error saving guardian:', error);
//       throw error;
//     }
//   },

//   clearGuardian: async () => {
//     try {
//       // Clear secure storage
//       await secureStorage.removeItem(STORAGE_KEYS.GUARDIAN_TOKEN);
//       await secureStorage.removeItem(STORAGE_KEYS.GUARDIAN_ID);
      
//       // Clear state
//       set({ guardian: null, isAuthenticated: false });
//     } catch (error) {
//       console.error('Error clearing guardian:', error);
//       throw error;
//     }
//   },

//   loadGuardian: async () => {
//     try {
//       set({ isLoading: true });
      
//       const token = await secureStorage.getItem(STORAGE_KEYS.GUARDIAN_TOKEN);
//       const id = await secureStorage.getItem(STORAGE_KEYS.GUARDIAN_ID);
      
//       if (token && id) {
//         set({
//           guardian: { id, token },
//           isAuthenticated: true,
//           isLoading: false,
//         });
//       } else {
//         set({ isLoading: false });
//       }
//     } catch (error) {
//       console.error('Error loading guardian:', error);
//       set({ isLoading: false });
//     }
//   },

//   setLoading: (loading: boolean) => set({ isLoading: loading }),
// }));

import { create } from 'zustand';
import { Guardian } from '../types';
import { secureStorage } from '../utils/storage';
import { STORAGE_KEYS } from '../utils/constants';
import { apiService } from '../services/api';

interface AuthState {
  guardian: Guardian | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  
  // Actions
  setGuardian: (guardian: Guardian) => Promise<void>;
  clearGuardian: () => Promise<void>;
  loadGuardian: () => Promise<void>;
  setLoading: (loading: boolean) => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  guardian: null,
  isAuthenticated: false,
  isLoading: true,

  setGuardian: async (guardian: Guardian) => {
    try {
      
      if (!guardian || guardian.id == null) {
        const err = new Error('Invalid guardian data: id is required');
        console.error(err);
        throw err;
      }

      const idStr = String(guardian.id);
      const tokenStr = guardian.token != null ? String(guardian.token) : '';

      
      
      await secureStorage.setItem(STORAGE_KEYS.GUARDIAN_ID, idStr);

      
      if (tokenStr) {
        await secureStorage.setItem(STORAGE_KEYS.GUARDIAN_TOKEN, tokenStr);
      } else {
        await secureStorage.removeItem(STORAGE_KEYS.GUARDIAN_TOKEN);
      }
      
      
      apiService.setGuardianId(idStr);
      
      
      set({ guardian: { id: idStr, token: tokenStr }, isAuthenticated: !!tokenStr });
    } catch (error) {
      console.error('Error saving guardian:', error);
      throw error;
    }
  },

  clearGuardian: async () => {
    try {
      
      
      await secureStorage.removeItem(STORAGE_KEYS.GUARDIAN_TOKEN);

      
      const storedId = await secureStorage.getItem(STORAGE_KEYS.GUARDIAN_ID);

      if (storedId) {
        
        apiService.setGuardianId(storedId);
        set({ guardian: { id: storedId, token: '' }, isAuthenticated: false });
      } else {
        
        apiService.setGuardianId(null as any);
        set({ guardian: null, isAuthenticated: false });
      }
    } catch (error) {
      console.error('Error clearing guardian:', error);
      throw error;
    }
  },

  loadGuardian: async () => {
    try {
      set({ isLoading: true });
      
      const token = await secureStorage.getItem(STORAGE_KEYS.GUARDIAN_TOKEN);
      const id = await secureStorage.getItem(STORAGE_KEYS.GUARDIAN_ID);
      
      if (id) {
        
        apiService.setGuardianId(id);

        set({
          guardian: { id, token: token || '' },
          isAuthenticated: !!token,
          isLoading: false,
        });
      } else {
        set({ isLoading: false });
      }
    } catch (error) {
      console.error('Error loading guardian:', error);
      set({ isLoading: false });
    }
  },

  setLoading: (loading: boolean) => set({ isLoading: loading }),
}));
